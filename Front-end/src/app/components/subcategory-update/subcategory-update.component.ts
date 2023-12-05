import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SubcategoryService } from 'src/app/services/subcategory.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-subcategory-update',
  templateUrl: './subcategory-update.component.html',
  styleUrls: ['./subcategory-update.component.css']
})


export class SubcategoryUpdateComponent implements OnInit{
  subcategoryId: any;
  SubCategoryFormGroup?: FormGroup;
  public submitted: boolean = false;


  constructor(
    private activatedRoute: ActivatedRoute,
    private subcategoryService: SubcategoryService,
    private fb: FormBuilder
  ) {
    this.subcategoryId = activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    if (this.subcategoryId) {
      this.subcategoryService.getSubcategory(this.subcategoryId).subscribe(subcategory => {
        const Subcategory = subcategory.data[0];
        this.SubCategoryFormGroup = this.fb.group({
          _id: [Subcategory._id, Validators.required],
          subCategoryName: [Subcategory.subCategoryName, Validators.required],
          categoryId:[Subcategory.categoryId, Validators.required],
          active: [Subcategory.active, Validators.required],
        });
      });
    }
  }

  editSubcategory() {
    if (this.subcategoryId && this.SubCategoryFormGroup) {
      this.subcategoryService.onUpdateSubcategory(this.SubCategoryFormGroup.value)
      .subscribe(data => {
        alert("Subcategory updated successfully");
      });
    }
  }
}


