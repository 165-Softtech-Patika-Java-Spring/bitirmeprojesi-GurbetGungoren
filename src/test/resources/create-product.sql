insert into product (id, product_name, product_price, tax_free_selling_price,product_category_id)
values (1001, 'name1' , 10.1,10, (select id from product_category where id=1001));