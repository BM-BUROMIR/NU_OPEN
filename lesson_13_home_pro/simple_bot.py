from telegram.ext import Application, CommandHandler, CallbackQueryHandler, MessageHandler, filters
from telegram import InlineKeyboardMarkup, Update, InlineKeyboardButton, InlineKeyboardMarkup, ReplyKeyboardMarkup
from dotenv import load_dotenv
import os
import hashlib

# подгружаем переменные окружения
load_dotenv()

# токен бота
TOKEN = os.getenv('TG_TOKEN')
lang = 'ru'

# функция-обработчик команды /start
async def start(update, context):
    inline_frame = [[InlineKeyboardButton("Русский", callback_data="lang_ru")],
                    [InlineKeyboardButton("English", callback_data="lang_en")]]
    # создаем inline клавиатуру
    inline_keyboard = InlineKeyboardMarkup(inline_frame)

    await update.message.reply_text('Выберите язык/Choose language:', reply_markup=inline_keyboard)


# функция-обработчик нажатий на кнопки
async def lang_button(update: Update, _):
    # получаем callback query из update
    global lang
    query = update.callback_query

    if query.data == 'lang_ru':
      lang = 'ru'
    elif query.data == 'lang_en':    
      lang = 'en'

    # всплывающее уведомление
    if lang == 'ru': 
        await query.answer('Вы выбрали русский язык!')
        await query.edit_message_text(text=f"🇷🇺")
    elif lang == 'en':     
        await query.answer('You choosed english language!')
        await query.edit_message_text(text=f"🇺🇸")
    

# функция-обработчик текстовых сообщений
async def text(update, context):
    global lang
    if lang == 'ru': 
        await update.message.reply_text(f"Текстовое сообщение получено! {lang}")
    elif lang == 'en':     
        await update.message.reply_text("We’ve received a message from you!")

# функция-обработчик команды /help
async def help(update, context):
    await update.message.reply_text("Этот бот предназначен для обучения❗")

def get_hashed_filename(url, ext='.jpg'):
    md5_hash = hashlib.md5(url.encode()).hexdigest()  # Получаем MD5 хэш URL
    return md5_hash + ext  # Возвращаем имя файла с расширением .txt

# функция-обработчик сообщений с изображениями
async def image(update, context):
    global lang
    if lang == 'ru': 
        await update.message.reply_text("Фотография сохранена")
    elif lang == 'en':     
        await update.message.reply_text("Photo saved!")

    # получаем изображение из апдейта
    file = await update.message.photo[-1].get_file()
    
    # сохраняем изображение на диск
    if not os.path.exists("photos/"):
        os.mkdir("photos/")
    await file.download_to_drive("photos/" + get_hashed_filename(file.file_path,'.jpg'))


# функция-обработчик голосовых сообщений
async def voice(update, context):
    global lang
    if lang == 'ru': 
        #await update.message.reply_text("Голосовое сообщение получено")
        await context.bot.sendPhoto(chat_id=update.effective_chat.id, photo="voice.jpg", caption="Голосовое сообщение получено")

    elif lang == 'en':     
        await context.bot.sendPhoto(chat_id=update.effective_chat.id, photo="voice.jpg", caption="We’ve received a voice message from you!")


def main():

    # создаем приложение и передаем в него токен
    application = Application.builder().token(TOKEN).build()
    print('Бот запущен...')

    # добавляем обработчик команды /start
    application.add_handler(CommandHandler("start", start))

    # добавляем CallbackQueryHandler (только для inline кнопок)
    application.add_handler(CallbackQueryHandler(lang_button))

    # добавляем обработчик команды /help
    application.add_handler(CommandHandler("help", help))

    # добавляем обработчик текстовых сообщений
    application.add_handler(MessageHandler(filters.TEXT, text))

    # добавляем обработчик сообщений с фотографиями
    application.add_handler(MessageHandler(filters.PHOTO, image))

    # добавляем обработчик голосовых сообщений
    application.add_handler(MessageHandler(filters.VOICE, voice))

    # запускаем бота (нажать Ctrl-C для остановки бота)
    application.run_polling()
    print('Бот остановлен')


if __name__ == "__main__":
    main()