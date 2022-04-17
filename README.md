### About
A small game used to familiarize myself with the [LibGDX Java framework](libgdx.com). The main focus of the project is to learn the fundamentals of the Entity-Component-System (ECS) Pattern using the included Ashley library as a basis. This project is currently in a low-fidelity prototype stage.

#### What is the Entity-Component-System (ECS) Pattern?
ECS is a design pattern focused on composition of entities of data, rather than inheritance of objects and their logic. Components are essentially bags of data with no logic, and Entities are composed of these components. The logic of what to do with these components falls under various systems.
Using this pattern, we can determine what each entity does by the components it contains, rather than by the classes that they inherit from. This allows the separation of data from the logic, and we can even determine which entities fall under which systems dynamically at runtime. This allows us to think about our product in terms of the data, rather than the objects.

One example of ECS use is in the movement system. Rather than inheriting a Movement interface and defining movement for each object that implements it, we simply have to determine what data movement should use (like its x,y position and velocity) and give that data to each entity we want to move. Then we can have a single system determine how every entity with this data should move. Making different movement patterns simply involves adding a separate component that indicates that the entity should move under a different system.
