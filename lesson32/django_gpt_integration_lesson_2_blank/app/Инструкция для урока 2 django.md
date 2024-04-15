Создадим окружение рядом с папкой app и установим необходимые зависимости
```
python.exe -m venv env
cd .\app\
..\env\Scripts\pip.exe install -r .\requirements.txt
```

Запустим сервер
```
..\env\Scripts\python.exe .\manage.py runserver
```

База данных уже есть в проекте. Доступ к админке admin:admin

## Подключение стилей и шрифта

shop/templates/base.html
```html
<head>
    ...
    <link rel="stylesheet" type="text/css" href="/static/css/style.css">
    <link href="/static/css/roboto.css" rel="stylesheet">
</head>
```

## Генерация размеченного текста для главной c помощью GPT
>Напиши тексты главной страницы для сервиса доставки СушиShop. Текст должен быть информативным, привлекательным и убедительным, привлекая внимание потенциальных клиентов и мотивируя их сделать заказ. Для разметки используй теги h2,p, ul, li. Можешь использовать emodji 

Полученный текст вставим на главную в content__container
```
<div class="content__container">
    ...
</div>
```

## Выведем популярные товары на главной странице

Добавим поле "товар по акции" для товара
Определите новое поле "товар по акции" в модели Product:
```python
class Product(models.Model):
    name = models.CharField(max_length=100)
    image = models.ImageField(upload_to='products', blank=True, null=True)
    description = models.TextField()
    price = models.DecimalField(max_digits=8, decimal_places=2)
    on_sale = models.BooleanField(default=False)  # Новое поле "товар по акции"

    def __str__(self):
        return self.name
```

Создайте и примените миграцию для обновления базы данных с новым полем. В терминале выполните следующие команды:
```
..\env\Scripts\python.exe .\manage.py makemigrations
..\env\Scripts\python.exe .\manage.py migrate
```

Теперь вы можете использовать новое поле on_sale в ваших запросах к модели Product. Например, чтобы получить все товары по акции, вы можете использовать фильтр:
```python
products_on_sale = Product.objects.filter(on_sale=True)
```

После этого можно вывести товары по скидке на главной странице.

Добавим переменную products_on_sale в контекст. В нее поместим queryset в котором будут все товары у которых on_sale=True

shop/views.py
```python
def index(request):
    context = {
        'title': 'Доставка суши SusiShop',
        'products_on_sale': Product.objects.filter(on_sale=True)
    }
    return render(request, 'index.html', context)
```

shop/templates/index.html
```html
{% if products_on_sale %}
    <h2>💰 Скидки 🎉</h2>
    <p>Не упустите шанс сэкономить на вашем заказе! Используйте наши акции и специальные предложения, чтобы насладиться вкусными суши по выгодной цене.</p>
    <div class="popular-product__container">
        {% for product in products_on_sale %}
            <a class="popular-product__item__header popular-product__item" href="{% url 'product_detail' pk=product.pk %}">
                <h4>{{ product.name }}</h4>
                <div class="popular-product__item__photo__container">
                    {% if product.image %}
                        <img class="popular-product__item__photo" src="{{product.image.url}}">
                    {% endif %}
                </div>
                <div class="popular-product__item__price">Цена: {{ product.price|floatformat:0 }} ₽</div>
            </a>
        {% endfor %}
    </div>
{% endif %}
```

Также можно вывести поле on_sale для удобного редактирования прямо в списке товаров в админке. 
```python
@admin.register(Product)
class ProductAdmin(admin.ModelAdmin):
    save_as = True
    list_display = ['id', 'name', 'price', 'on_sale']
    list_editable = ['name', 'price', 'on_sale',]
```

## Добавление на сайт Корзины 

Добавим приложение cart в INSTALLED_APPS
sushi_delivery_shop/settings.py
```python
INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'shop',
    'cart', 
]
```

Создадим и применим миграции
```
..\env\Scripts\python.exe .\manage.py makemigrations
..\env\Scripts\python.exe .\manage.py migrate
```

Добавим пути приложения cart в основной urls.py
sushi_delivery_shop/urls.py
```python
urlpatterns = [
    path('admin/', admin.site.urls),

    path('', include('cart.urls')),
    path('', include('shop.urls')),
]
```

Добавим кнопку добавления товара в корзину в карточки каталога
shop/templates/product_list.html
```html
<div class="catalog__item">
    ...
    
    {% include 'includes/buy_button.html' %}
</div>
```

Добавим кнопку добавления товара в корзину на страницу товара
shop/templates/product_detail.html
```html
<div class="product-detail__info">
    ...
    
    {% include 'includes/buy_button.html' %}
</div>
```