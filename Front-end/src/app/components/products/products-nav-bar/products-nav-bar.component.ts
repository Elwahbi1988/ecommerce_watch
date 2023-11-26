import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from 'src/app/model/product.model';
import { ActionEvent, AppDataState, ProductActionTypes } from 'src/app/state/product.state';

@Component({
  selector: 'app-products-nav-bar',
  templateUrl: './products-nav-bar.component.html',
  styleUrls: ['./products-nav-bar.component.css']
})
export class ProductsNavBarComponent implements OnInit {


  @Input() products$: Observable<AppDataState<Product[]>> | null = null;
 @Output() productsEventEmitter : EventEmitter<ActionEvent> = new EventEmitter();
    emitProductEvent(type: ProductActionTypes, payload?: any) {
  this.productsEventEmitter.emit({ type, payload });
}

 constructor(){}

ngOnInit(): void {
}

onNewProduct() {
  this.emitProductEvent(ProductActionTypes.NEW_PRODUCT);
  console.log(this.emitProductEvent(ProductActionTypes.NEW_PRODUCT))
  }
onGetAllProducts() {
this.emitProductEvent(ProductActionTypes.GET_ALL_PRODUCT);
}
onGetSelectedProducts() {
  this.emitProductEvent(ProductActionTypes.GET_SELECTED_PRODUCT);
}

onGetAvailableProducts() {
  this.emitProductEvent(ProductActionTypes.GET_AVAILABLE_PRODUCT);
}
onSearch(dataForm:any) {
  this.emitProductEvent(ProductActionTypes.SEARCH_PRODUCT, dataForm);
}
}