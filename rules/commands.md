# Команды

Глобальной сборки нет — работа ведётся внутри конкретного урока. Типовой цикл:

```bash
cd lesson_NN
python3 -m venv venv && source venv/bin/activate
pip install -r requirements.txt
cp .env_example .env            # затем подставить реальные ключи

# FastAPI-уроки (11, 12, 14, ...)
uvicorn main:app --reload

# Telegram-боты (13, 14)
python simple_bot.py

# Django-уроки (12, 31, 32, 33) — внутри подпапки с manage.py
python manage.py migrate
python manage.py createsuperuser
python manage.py runserver
```

Часть уроков содержит `run.sh` (например `lesson_14_home_lite/run.sh`).

## Deploy

Деплой как таковой отсутствует — это учебные проекты, запускаемые локально (uvicorn / runserver). Промышленной CI/CD-конфигурации в репозитории нет.
