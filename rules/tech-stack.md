# Tech Stack

Python 3 (основной язык). По урокам:
- **LLM**: `openai==0.27.4`, `langchain==0.0.271`, `tiktoken`, `faiss-cpu` (retrieval по текстовой базе).
- **Web/API**: `fastapi==0.90.1` + `uvicorn` + `pydantic`; Django (5.0.3 в поздних уроках) + `django-cors-headers` + `pillow`.
- **Telegram**: `python-telegram-bot==20.3` (+ `[job-queue]`).
- **Конфиг**: `python-dotenv`.
- **Прочее**: Jupyter-ноутбуки (`.ipynb`) для экспериментов; Android Studio (Kotlin/Java, уроки 29–30) с FastAPI-бэкендом.

Версии не закреплены глобально — у каждого урока свой `requirements.txt`.
