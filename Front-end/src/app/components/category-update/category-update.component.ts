import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from 'src/app/services/category.service';
import { Category } from '../../model/category.model';

@Component({
  selector: 'app-category-update',
  templateUrl: './category-update.component.html',
  styleUrls: ['./category-update.component.css']
})
export class CategoryUpdateComponent implements OnInit{
  categoryId: any;
  categoryFormGroup?: FormGroup;
  public submitted: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private categoryService: CategoryService,
    private fb: FormBuilder
  ) {
    this.categoryId = activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    if (this.categoryId) {
      this.categoryService.getCategory(this.categoryId).subscribe(category => {
        const Category = category.data[0];
        this.categoryFormGroup = this.fb.group({
          _id: [Category._id, Validators.required],
          categoryName: [Category.CategoryName, Validators.required],
          active: [Category.active, Validators.required],
        });
      });
    }
  }

  editCategory() {
    if (this.categoryId && this.categoryFormGroup) {
      this.categoryService.onUpdateCategory(this.categoryFormGroup.value)
      .subscribe(data => {
        alert("Category updated successfully");
      });
    }
  }
}
