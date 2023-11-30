import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsComponent } from './components/products/products.component';
import { HomeComponent } from './components/home/home.component';
import { ProductAddComponent } from './components//product-add/product-add.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { CustomersComponent } from './components/customers/customers.component';
import { OrdersComponent } from './components/orders/orders.component';
import { OrderDetailsComponent } from './components/order-details/order-details.component';
import { CategoryComponent } from './components/category/category.component';
import { CategoryAddComponent } from './components/category-add/category-add.component';
import { CategoryUpdateComponent } from './components/category-update/category-update.component';
import { SubcategoryComponent } from './components/subcategory/subcategory.component';
import { SubcategoryAddComponent } from './components/subcategory-add/subcategory-add.component';
import { SubcategoryUpdateComponent } from './components/subcategory-update/subcategory-update.component';


const routes: Routes = [
  {path:"products", component:ProductsComponent},
  {path:"home",component:HomeComponent},
  {path:"update-product/:id", component: UpdateProductComponent },
  {path:"newProduct",component:ProductAddComponent},
  {path:"customers",component:CustomersComponent},
  {path:"orders/:customerId",component:OrdersComponent},
  {path:"order-details/:orderId",component:OrderDetailsComponent},
  {path:"category",component:CategoryComponent},
  {path:"newCategory",component:CategoryAddComponent},
  {path:"category-update/:id", component: CategoryUpdateComponent },
  {path:"subcategory",component:SubcategoryComponent},
  {path:"newSubcategory",component:SubcategoryAddComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
