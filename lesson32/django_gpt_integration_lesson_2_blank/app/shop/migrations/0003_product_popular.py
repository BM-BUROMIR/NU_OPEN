# Generated by Django 4.2.11 on 2024-04-16 16:50

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("shop", "0002_product_on_sale"),
    ]

    operations = [
        migrations.AddField(
            model_name="product",
            name="popular",
            field=models.BooleanField(default=False),
        ),
    ]