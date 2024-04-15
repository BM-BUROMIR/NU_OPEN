from django.contrib import admin
from shop.models import Product


@admin.register(Product)
class ProductAdmin(admin.ModelAdmin):
    pass
    save_as = True
    list_display = ['id', 'name', 'price', 'image']
    list_editable = ['name', 'price', 'image']