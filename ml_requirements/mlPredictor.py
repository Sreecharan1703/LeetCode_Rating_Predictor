import pickle
import pandas as pd
import re

def load_dataset():
    pattern = r',\s*(?![^\[]*\])'
    parsed_rows = []

    fileName = 'ml_requirements/inputFile.txt'

    with open(fileName, 'r', encoding='utf-8') as file:
        for line in file:
            line = line.strip()
            if not line:
                continue
            row = re.split(pattern, line)
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

def saveToFile(rating):
    fileName = 'ml_requirements/outputFile.txt'
    with open(fileName,'w') as file:
        file.write(str(rating))

def run_prediction():
    with open('ml_requirements/mlbFile.pkl','rb') as file:
        mlb = pickle.load(file)
    with open('ml_requirements/modelFile.pkl','rb') as file:
        xgb_model = pickle.load(file)

    df = load_dataset()
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

    saveToFile(rating)

if __name__ == "__main__":
    run_prediction()