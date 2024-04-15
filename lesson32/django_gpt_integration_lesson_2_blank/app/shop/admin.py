from django.contrib import admin
from shop.models import Product


@admin.register(Product)
class ProductAdmin(admin.ModelAdmin):
    save_as = True
    list_display = ['id', 'name', 'price',]
    list_editable = ['name', 'price',]