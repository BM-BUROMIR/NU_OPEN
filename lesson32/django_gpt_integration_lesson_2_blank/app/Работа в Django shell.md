
В shell можно экспериментировать с функционалом и работать с объектами моделей Django.

Запуск Django shell:
```
..\env\Scripts\python.exe .\manage.py shell
```

Импорт модели Product:
```python
from shop.models import Product
```

Создание нового объекта Product и сохраните его в базе данных:
```python
new_product = Product(name='Сет №1', description='Лучший сет', price=3333)
new_product.save()
```


Получение всех объектов модели Product:
```python
all_products = Product.objects.all()
for product in all_products:
    print(product.name, product.description, product.price)
```

Получение конкретного объекта Product и вывод его атрибутов:
```python
specific_product = Product.objects.get(name='Сет №1')
print(specific_product.name)
print(specific_product.description)
print(specific_product.price)
```

получение Product у которого цена ниже и выше 2000
```python
products_under_2000 = Product.objects.filter(price__lt=2000)
for product in products_under_2000:
    print(product.name, product.description, product.price)

products_above_2000 = Product.objects.filter(price__gt=2000)

# так можно получить список значений атрибута
products_above_2000.values_list('price', flat=True)
```

Обновление атрибута price у объекта specific_product:
```python
specific_product.price = 2222
specific_product.save()
```

Удаление объекта specific_product:
```python
specific_product.delete()
```
