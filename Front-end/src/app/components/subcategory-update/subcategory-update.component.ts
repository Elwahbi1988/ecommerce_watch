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
  SubcategoryFormGroup?: FormGroup;
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
        this.SubcategoryFormGroup = this.fb.group({
          id: [Subcategory.id, Validators.required],
          subcategoryName: [Subcategory.subcategoryName, Validators.required],
          categoryId:[Subcategory.categoryId, Validators.required],
          selected: [Subcategory.selected, Validators.required],
          active: [Subcategory.Active, Validators.required],
        });
      });
    }
  }

  editSubcategory() {
    if (this.subcategoryId && this.SubcategoryFormGroup) {
      this.subcategoryService.onUpdateSubcategory(this.SubcategoryFormGroup.value)
      .subscribe(data => {
        alert("Subcategory updated successfully");
      });
    }
  }

}


