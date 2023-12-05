import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subcategory } from 'src/app/model/subcategory.model';
import { ProductsService } from 'src/app/services/products.service';


@Component({
  selector: 'app-product-add',
  templateUrl: './product-add.component.html',
  styleUrls: ['./product-add.component.css']
})
export class ProductAddComponent implements OnInit {
  productFormGroup?:FormGroup;
  submitted:boolean =false;
  sub : any[] = [];
 cats: any[] = [];
 filteredSubcategories: Subcategory[] = [] ;

  constructor(private fb:FormBuilder,private productsService:ProductsService) {}


  ngOnInit(): void {
   this.productFormGroup = this.fb.group({
sku: [0,Validators.required],
productImage:["",Validators.required],
productName: ["",Validators.required],
categoryId: ["", Validators.required],
subCategoryId: ["", Validators.required],
shortDescription: ["",Validators.required],
longDescription: ["",Validators.required],
 price:[0,Validators.required],
 discountPrice: [0,Validators.required],
 quantity: [0,Validators.required],

  });
  this.LoadCategory();
  this.onCategoryChange();
  this.LoadSubcategory();
}
LoadCategory() {
  this.productsService.getAllCategory().subscribe(data  => {
    this.cats = data.data;
    console.log(this.cats);
  },error => {
    console.log(error);
  });
}
onCategoryChange() {
  const selectedCategoryId = this.productFormGroup?.get('categoryId')?.value;
  // Filtrer les sous-catégories en fonction de la catégorie sélectionnée
  this.filteredSubcategories = this.sub.filter(sub => sub.categoryId == selectedCategoryId);
}
LoadSubcategory() {
  this.productsService.getAllSubcategory().subscribe(data  => {
    this.sub = data.data;
    console.log(this.sub);
  },error => {
    console.log(error);
  });
}


OneSaveProduct(){
  this.submitted=true;
  this.productsService.save(this.productFormGroup?.value).subscribe(data => {
  alert("Product saved");
})
}

}
