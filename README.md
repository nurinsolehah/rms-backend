Recipe Management System

User role: ADMIN and USER

Accessible for ALL roles(without JWT token):
1. Register
2. Login
   
Accessible for ALL roles(with JWT token)
1. View Recipe List
2. Add a Recipe
3. Delete a Recipe
4. Update a Recipe
5. Upload image for Recipe
   
Only Admin access: 
1. View User List
2. View Food Category List
3. Deactive a Food Category

1. Clone repository
2. Do docker build
   - docker build -t rms-backend .
3. Run docker
   - docker run -p 8080:8080 rms-backend


   
