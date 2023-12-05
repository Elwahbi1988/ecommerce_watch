



import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';
import { Product } from 'src/app/model/product.model';
import { ProductsService } from 'src/app/services/products.service';
import { ActionEvent, AppDataState, DataStateEnum, ProductActionTypes } from 'src/app/state/product.state';
import { Router, ActivatedRoute } from '@angular/router';
import { Route } from '@angular/router';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

 @Output() productEventEmitter : EventEmitter<ActionEvent> = new EventEmitter();
  products$: Observable<AppDataState<Product[]>> | null = null;
  readonly DataStateEnum = DataStateEnum;

  constructor(private productsService: ProductsService, private router:Router, private route: ActivatedRoute) {}

  ngOnInit(): void {}

  onGetAllProducts() {
    this.products$ = this.productsService.getAllProducts().pipe(
      map(data => ({ dataState: DataStateEnum.LOADED, data: data.data })),
      startWith({ dataState: DataStateEnum.LOADING }),
      catchError(err => of({ dataState: DataStateEnum.ERROR, errorMessage: err.message }))
    );
  }

  onSearch(dataForm: any) {
    this.products$ = this.productsService.SearchProducts(dataForm.keyword).pipe(
      map(data => ({ dataState: DataStateEnum.LOADED, data: data.data })),
      startWith({ dataState: DataStateEnum.LOADING }),
      catchError(err => of({ dataState: DataStateEnum.ERROR, errorMessage: err.message }))
    );
  }

  onDelete(p: Product) {
    let v = confirm("Are you sure you want to delete this?");
    if (v === true) {
      this.productsService.deleteProduct(p).subscribe(() => {
        this.onGetAllProducts();
      });
    }
  }
  onNewProduct(){
    this.router.navigateByUrl("/newProduct");
  }


editProduct(p: any) {
  console.log(p);
  this.router.navigateByUrl("/update-product/" + p._id);
}


onActionEvent($event: ActionEvent) {
 switch($event.type){
  case ProductActionTypes.GET_ALL_PRODUCT: this.onGetAllProducts(); break;
  case ProductActionTypes.SEARCH_PRODUCT: this.onSearch($event.payload); break;
  case ProductActionTypes.EDIT_PRODUCT: this.editProduct($event.payload); break;
  case ProductActionTypes.NEW_PRODUCT: this.onNewProduct(); break;
  case ProductActionTypes.DELETE_PRODUCT: this.onDelete($event.payload); break;
 }
  }
 
  }

