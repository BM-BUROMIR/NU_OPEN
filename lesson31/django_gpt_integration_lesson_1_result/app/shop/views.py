from django.shortcuts import render

from django.views.generic import ListView, DetailView
from shop.models import Product

def index(request):
    context = {
        'title': 'Доставка суши SusiShop',
    }
    return render(request, 'index.html', context)


class ProductListView(ListView):
    model = Product
    template_name = 'product_list.html'
    context_object_name = 'products'

    def get_queryset(self):
        queryset = super().get_queryset()
        
        sort_option = self.request.GET.get('sort')
        sort_price_option = self.request.GET.get('sort_price')

        if sort_option == 'asc':
            sort_option_name = 'name'
        elif sort_option == 'desc':
            sort_option_name = '-name'
        if sort_price_option == 'asc':
            sort_option_price = 'price'
        elif sort_price_option == 'desc':
            sort_option_price = '-price'
        
        queryset = queryset.order_by(sort_option_price,sort_option_name)
        return queryset


class ProductDetailView(DetailView):
    model = Product
    template_name = 'product_detail.html'
    context_object_name = 'product'