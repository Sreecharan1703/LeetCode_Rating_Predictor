import pickle
import pandas as pd
import re
import sys

def load_dataset(query):
    pattern = r',\s*(?![^\[]*\])'
    parsed_rows = []

    row = re.split(pattern, query)
    parsed_rows.append(row)

    columns = [
        "Q.id", "Likes", "Dislikes", "Difficulty",
        "AcceptanceRate", "TotalAccepted", "TotalSubmissions", "Tags"
    ]

    df = pd.DataFrame(parsed_rows, columns=columns)

    df = df.drop('Q.id', axis=1)

    return df

def makeSuitableforPrediction(df):
    df['AcceptanceRate'] = df['AcceptanceRate'].str.removesuffix("%").astype(float)
    df['Likes'] = df['Likes'].astype(int)
    df['Dislikes'] = df['Dislikes'].astype(int)
    df['TotalAccepted'] = df['TotalAccepted'].astype(int)
    df['TotalSubmissions'] = df['TotalSubmissions'].astype(int)
    df['Difficulty'] = df['Difficulty'].map({'Hard':900, 'Medium':300,'Easy':100}).astype(int)
    df['Tags'] = df['Tags'].apply(lambda x: [tag.strip() for tag in x.strip('[]').split(',')])
    df['Dependency'] = df['Difficulty']/df['AcceptanceRate']
    df['naturalAcceptance'] = df['Likes']/df['TotalSubmissions']
    df['DislikeRatio'] = df['Dislikes']/(df['Dislikes']+df['Likes']+1)
    return df

def run_prediction(query):
    with open('ml_requirements/mlbFile.pkl','rb') as file:
        mlb = pickle.load(file)
    with open('ml_requirements/modelFile.pkl','rb') as file:
        xgb_model = pickle.load(file)

    df = load_dataset(query)
    df = makeSuitableforPrediction(df)

    newmatrix = mlb.transform(df['Tags'])
    tags_df = pd.DataFrame(
        newmatrix,
        columns=mlb.classes_,
        index=df.index
    )
    df = pd.concat([df.drop('Tags',axis=1), tags_df], axis=1)

    outputPred = xgb_model.predict(df)
    rating = outputPred[0]

    return rating

if __name__ == "__main__":
    query = sys.argv[1]
    rating = run_prediction(query)
    print(rating)