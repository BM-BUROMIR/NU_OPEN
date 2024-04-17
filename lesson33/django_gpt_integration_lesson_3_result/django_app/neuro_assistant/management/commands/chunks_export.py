from django.core.management.base import BaseCommand
from neuro_assistant.models import Category, Chunk
from shop.models import Product

class Command(BaseCommand):
    help = 'Export all chunks to a text file'

    def handle(self, *args, **options):
        relative_path = '../fastapi_sushi/chunks_export.md' # Указываем относительный путь до папки с fastapi приложением
        with open(relative_path, 'w') as file:
            products = Product.objects.all()

            for product in products:
                file.write(f'## Позиция каталога – {product.name}\n')
                desc = product.description.replace('\r','')
                file.write(f'Описание: {desc}\n')
                file.write(f'Цена: {int(product.price)} рублей\n')
 
                file.write(f'Ссылка на товар: [{product.name}](http://127.0.0.1:8000{product.get_absolute_url()})\n\n')

            for category in Category.objects.all():
                chunks = Chunk.objects.filter(category=category)
                for chunk in chunks:
                    file.write(f'## {category.name}\n')
                    file.write(f'{chunk.text}\n')