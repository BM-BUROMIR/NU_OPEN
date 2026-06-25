# Env vars (только имена)

Хранятся в `.env` каждого урока (см. соответствующий `.env_example`). Имена различаются между уроками:

- `OPENAI_API_KEY` — ключ OpenAI (поздние уроки)
- `GPT_API_TOKEN` — ключ OpenAI (ранние уроки 11–12)
- `GPT_SECRET_KEY` — ключ OpenAI (вариант в уроке 14)
- `TG_TOKEN` — токен Telegram-бота

> ⚠️ Безопасность: реальные ключи в `.env`/`.env_example` коммитить нельзя. В `.env_example` должны быть только плейсхолдеры (`your-openai-key-here`). Извлечённые реальные секреты лежат в gitignored `.secrets/extracted-secrets.env` и подлежат ротации.
