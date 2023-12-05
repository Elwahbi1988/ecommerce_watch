import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent {
  productId: any;
  productFormGroup?: FormGroup;
  public submitted: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private productsService: ProductsService,
    private fb: FormBuilder
  ) {
    this.productId = activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    if (this.productId) {
      this.productsService.getProducts(this.productId).subscribe(products => {
        const product = products.data[0];
        this.productFormGroup = this.fb.group({
          _id: [product._id, Validators.required],
          sku: [product.sku, Validators.required],
          productImage: [product.productImage, Validators.required],
          productName: [product.productName, Validators.required],
          subCategoryId: [product.subCategoryId, Validators.required],
          shortDescription: [product.shortDescription, Validators.required],
          longDescription: [product.longDescription, Validators.required],
          price: [product.price, Validators.required],
          discountPrice: [product.discountPrice, Validators.required],
          quantity: [product.quantity, Validators.required],
          available: [product.active, Validators.required],
        });
      });
    }
  }

  editProduct() {
    if (this.productId && this.productFormGroup) {
      this.productsService.onUpdateProduct(this.productFormGroup.value)
      .subscribe(data => {
        alert("Product updated successfully");
      });
    }
  }
}
