import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { ProductsComponent } from './components/products/products.component';
import { HomeComponent } from './components/home/home.component';
import { ProductAddComponent } from './components/product-add/product-add.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { NgClass } from '@angular/common';
import { ProductsNavBarComponent } from './components/products/products-nav-bar/products-nav-bar.component';
import { ProductsListComponent } from './components/products/products-list/products-list.component';
import { CustomersComponent } from './components/customers/customers.component';
import { OrdersComponent } from './components/orders/orders.component';
import { OrderDetailsComponent } from './components/order-details/order-details.component';
import { CategoryComponent } from './components/category/category.component';
import { CategoryListComponent } from './components/category/category-list/category-list.component';
import { CategoryNavBarComponent } from './components/category/category-nav-bar/category-nav-bar.component';
import { SubcategoryComponent } from './components/subcategory/subcategory.component';
import { SubcategoryListComponent } from './components/subcategory/subcategory-list/subcategory-list.component';
import { SubcategoryNavBarComponent } from './components/subcategory/subcategory-nav-bar/subcategory-nav-bar.component';
import { CategoryAddComponent } from './components/category-add/category-add.component';
import { CategoryUpdateComponent } from './components/category-update/category-update.component';
import { SubcategoryAddComponent } from './components/subcategory-add/subcategory-add.component';
import { SubcategoryUpdateComponent } from './components/subcategory-update/subcategory-update.component';


@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    ProductsComponent,
    HomeComponent,
    ProductAddComponent,
    ProductsComponent,
    UpdateProductComponent,
    ProductsNavBarComponent,
    ProductsListComponent,
    CustomersComponent,
    OrdersComponent,
    OrderDetailsComponent,
    CategoryComponent,
    CategoryListComponent,
    CategoryNavBarComponent,
    SubcategoryComponent,
    SubcategoryListComponent,
    SubcategoryNavBarComponent,
    CategoryAddComponent,
    CategoryUpdateComponent,
    SubcategoryAddComponent,
    SubcategoryUpdateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgClass,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
