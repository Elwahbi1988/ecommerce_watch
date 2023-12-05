import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../model/category.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  public host:string= "http://localhost:8085/";

  constructor(private http:HttpClient) { }

  getAllCategory(): Observable<any>{
    return this.http.get<any>(this.host + "v1/categories");
  }
  getSelectCategory(): Observable<Category[]>{
    return this.http.get<Category[]>(this.host + "v1/categories/");
  }
  getAvailableCategory(): Observable<Category[]>{
return this.http.get<Category[]>(this.host + "v1/categories");
  }
  SearchCategory(keyword:string): Observable<any>{
    return this.http.get<any>(this.host + "v1/categories/query?query=" + keyword);
      }
selectCategory(category:any): Observable<Category>{
  category.selected=!category.selected
        return this.http.put<any>(this.host+"v1/categories/"+category._id,category);
          }

deleteCategory(category:any): Observable<void>{
                  return this.http.delete<void>(this.host+"v1/categories/"+ category._id);
                    }

saveCategory(category:any): Observable<any>{
 return this.http.post<any>(this.host+"v1/categories",category);
                              }
getCategory(id:number): Observable<any>{
return this.http.get<Category>(this.host + "v1/categories/" +id);
}
onUpdateCategory(category: any): Observable<any> {
  return this.http.patch<any>(`${this.host}v1/categories/` + String(category._id), category);
}

}

