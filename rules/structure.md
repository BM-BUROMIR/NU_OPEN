# Структура проекта

```
NU-OPEN/
├── README.md                # практически пустой
├── .gitignore               # стандартный Python gitignore
├── bases/                   # общие данные (wine_base.csv и пр.)
├── lesson_11 / _home_lite / _home_pro   # OpenAI API + FAISS retrieval + FastAPI (main.py, chunks.py)
├── lesson_12 / _home_pro    # django_chatbot/ + fastapi/
├── lesson_13 / _home_lite / _home_pro   # Telegram-бот (simple_bot.py, handlers.py, buttons.py)
├── lesson_14 / _home_lite / _home_pro   # Telegram-бот + fastapi/
├── lesson24                 # ноутбук: запуск локальных моделей
├── lesson_29_home_lite      # Android Studio (Helloworld) + лекции
├── lesson_30_home_lite      # Android + android_lesson_2_fastapi/
├── lesson31 / lesson32 / lesson33  # Django + интеграция GPT (blank/result версии, часто .zip)
```

Внутри уроков типичные файлы: `main.py` / `simple_bot.py` (точка входа), `chunks.py` (индексная база FAISS), `requirements.txt`, `task.txt` / `*.docx` (формулировка ДЗ), `screen/` и `images/` (скрины-ответы), `.env_example`.
