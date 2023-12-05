import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-category-add',
  templateUrl: './category-add.component.html',
  styleUrls: ['./category-add.component.css']
})
export class CategoryAddComponent implements OnInit {
  categoryFormGroup?:FormGroup;
  submitted:boolean =false;

  constructor(private fb:FormBuilder,private categoryService:CategoryService) {}

  ngOnInit(): void {
   this.categoryFormGroup = this.fb.group({
_id: [0,Validators.required],
categoryName: ["",Validators.required],
SubCategory: ["", Validators.required],
 active: [true,Validators.required],

  });
}
OneSaveCategory(){
  this.submitted=true;
  this.categoryService.saveCategory(this.categoryFormGroup?.value).subscribe(data => {
  alert("Category saved");
})
}
}
