import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsComponent } from './components/products/products.component';
import { HomeComponent } from './components/home/home.component';
import { ProductAddComponent } from './components//product-add/product-add.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { CustomersComponent } from './components/customers/customers.component';
import { OrdersComponent } from './components/orders/orders.component';


const routes: Routes = [
  {path:"products", component:ProductsComponent},
  {path:"home",component:HomeComponent},
  {path:"update-product/:id", component: UpdateProductComponent },
  {path:"newProduct",component:ProductAddComponent},
  {path:"customers",component:CustomersComponent},
  {path:"orders/customerId",component:OrdersComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
