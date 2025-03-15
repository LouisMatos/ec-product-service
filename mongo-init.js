db.createUser({
    user: 'product_service_user',
    pwd: 'product_service_pass',
    roles: [
      {
        role: 'readWrite',
        db: 'product-service'
      }
    ]
  });
  
  db = db.getSiblingDB('product-service');
  
  db.createCollection('products'); 