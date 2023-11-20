from fastapi import FastAPI
from pydantic import BaseModel
from dotenv import load_dotenv


class Item(BaseModel): 
    x: float
    y: float

# создаем объект приложения
app = FastAPI()

# функция обработки get запроса + декоратор 
@app.get("/")
def read_root():
    return {"result": "ok"}

# функция обработки post запроса + декоратор 
@app.post("/api/plus")
def get_plus(question: Item):
    res = question.x + question.y
    answer = f"x+y={res}"
    return {"message": answer,
            "answer" : res,
            "result": "ok"
            }


@app.post("/api/minus")
def get_minus(question: Item):
    res = question.x - question.y
    answer = f"x-y={res}"
    return {"message": answer,
            "answer" : res,
            "result": "ok"
            }

@app.post("/api/add")
def get_add(question: Item):
    res = question.x * question.y
    answer = f"x*y={res}"
    return {"message": answer,
            "answer" : res,
            "result": "ok"
            }

@app.post("/api/div")
def get_div(question: Item):
    if question.y != 0:
        res = question.x / question.y
        result = "ok"
    else: 
        res = "На 0 делить нельзя!"    
        result = "bad"
    answer = f"x/y={res}"
    return {"message": answer,
            "answer" : res,
            "result": result
            }
