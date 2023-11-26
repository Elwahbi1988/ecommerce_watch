import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../model/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  public host:string= "http://localhost:8085/";

  constructor(private http:HttpClient) { }

  getAllProducts(): Observable<any>{
    return this.http.get<any>(this.host + "v1/products");
  }
  getSelectProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(this.host + "v1/products/search");
  }
  getAvailableProducts(): Observable<Product[]>{
return this.http.get<Product[]>(this.host + "v1/products");
  }
  SearchProducts(keyword:string): Observable<any>{
    return this.http.get<any>(this.host + "v1/products?query=" + keyword);
      }
select(product:Product): Observable<Product>{
  product.selected=!product.selected
        return this.http.put<Product>(this.host+"v1/pdt/product/"+product._id,product);
          }

deleteProduct(product:Product): Observable<void>{
            product.selected=!product.selected
                  return this.http.delete<void>(this.host+"v1/product/"+product._id);
                    }

save(product:Product): Observable<any>{
 return this.http.post<any>(this.host+"v1/products",product);
                              }
getProducts(id:number): Observable<any>{
return this.http.get<Product>(this.host + "v1/products/" + id);
}
onUpdateProduct(product: any): Observable<any> {
  return this.http.put<any>(`${this.host}v1/products/` + String(product._id), product);
}

}
