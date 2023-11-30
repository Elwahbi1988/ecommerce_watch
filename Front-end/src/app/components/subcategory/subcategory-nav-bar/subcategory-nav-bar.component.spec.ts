import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubcategoryNavBarComponent } from './subcategory-nav-bar.component';

describe('SubcategoryNavBarComponent', () => {
  let component: SubcategoryNavBarComponent;
  let fixture: ComponentFixture<SubcategoryNavBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubcategoryNavBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubcategoryNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
