import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductsService } from 'src/app/services/products.service';


@Component({
  selector: 'app-product-add',
  templateUrl: './product-add.component.html',
  styleUrls: ['./product-add.component.css']
})
export class ProductAddComponent implements OnInit {
  productFormGroup?:FormGroup;
  submitted:boolean =false;

  constructor(private fb:FormBuilder,private productsService:ProductsService) {}

  ngOnInit(): void {
   this.productFormGroup = this.fb.group({
sku: [0,Validators.required],
productImage:["",Validators.required],
productName: ["",Validators.required],
subCategoryId: ["", Validators.required],
shortDescription: ["",Validators.required],
longDescription: ["",Validators.required],
 price:[0,Validators.required],
 discountPrice: [0,Validators.required],
 quantity: [0,Validators.required],
 selected: [true,Validators.required],
 available: [true,Validators.required],
  });
}
OneSaveProduct(){
  this.submitted=true;
  this.productsService.save(this.productFormGroup?.value).subscribe(data => {
  alert("Product saved");
})
}
}
