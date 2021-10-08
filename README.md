# _*Culinio*_

# _Contents_

###  [**_Culinio_**](#Culinio)
  - [**_Concept_**](#Concept)
    - [**_Introduction_**](#introduction)
    - [**_Advantages_**](#Advantages)
    - [**_Conclusion_**](#Conclusion)
  - [**_Description_**](#description)
    - [**_Introduction_**](#introduction)
    - [**_Organizational Login_**](#organizational-login)
    - [**_Employee Login_**](#employee-login)
    - [**_Chef Login_**](#chef-login)
  - [**_Built with_**](#built-with)
  - [**_User Guide_**](#user-guide)
  - [**_Authors_**](#authors)

# _Concept_

## _Introduction_
Our application "Culinio" is a service-based application for food ordering within a company. The Corporate world is known to toil hard at work even without breaks. A meal break is supposed to be very essential for a being to keep him/her fit and healthy. But, almost every corporate worker forgets to take their meal at the right time. They don't forget to eat but are too busy to remember to take their meal. Sacrificing one’s meal isn’t a healthy way to go ahead in a corporate world. Some employees don't move from their desk just for the sake of taking their meal from the Office Cafeteria. That’s when "Culinio" comes into play.

Employees of the company will be able to order and schedule their 3-course meal for a day through the application. They often avoid it to save time rather than waiting in long queues to order their food. When the schedule of an employee is full and piling up, they choose to skip their meal. In these scenarios, the employee will be able to pre-schedule their meals through our application before they get drowned in work. Culinio helps the employees save their time waiting in the long cafeteria queues. The employees schedule their order with just a click and “Culinio” will take care of the rest. Even if the employee forgets to schedule his/her meal for the day, “Culinio” has an option to “Place an order” instantly. The orders will be placed at the scheduled time or instantly to the office cafeteria. When the ordered meal is ready the chef/attendee at the office cafeteria will notify the employee through our application. A notification with an alarm will be sent to the employee who ordered the food. The alarm will remind the employee about his/her meal and this in return ensures that a person doesn’t skip his/her meal.

The employees need not register separately in our application. The organization will register on behalf of all the employees and chefs.  A unique 4 digit code will be generated, which need to be used by the employees and the chefs at the Office Cafeteria to have encrypted communication. The code ensures that the order placed by a particular organization’s employee is received by the same organization’s cafeteria.

## _Advantages_
“Culinio” provides an easy and safe environment to get the food of the employees. It ensures that the employee never forgets his/her meal for the day. As we always say “Time is precious”. So “Culinio” never wastes an employee’s precious time. It reduces the waiting time at the cafeteria. By pre-scheduling the employee never skips their meal but also eats it on time. So they stay fit and healthy. Eating 3 meals per day is no big deal but eating it at the right time matters. So to have his meal at the right time he needs to plan his day in advance. So he also becomes more conscious and organized to get his work in time which will allow him to have his meal at the right time. Overall, this system “Culinio” is best suited and beneficial for a corporate environment and helps the employees to stay fit and healthy.

## _Conclusion_
To put it in a nutshell "Culinio" remembers and reminds the time of a meal that one should take. Now the employees will not feel guilty and forget to have their meal on time. Eating on time keeps the employee healthy and fit which in turn helps him to work more focused and determined towards his company.


# _Description_

## _Introduction_
“Culinio” is an android application that allows the employees of a particular company to pre-schedule and order their meal. To use this application a particular company needs to register in our application. There will be 3 logins separately for the Employees, Chef, and the Organization. When logged into their respective login they will be able to access different views of the application. Right after the organization registration a unique “4 digit code” will be generated for that particular organization. This 4 digit code provides encrypted communication within the organization so that each employee is made sure that his/her order is sent only to his/her company’s cafeteria.

## _Organizational Login_
When a particular organization logs into our application they need to provide the organization's email address and password. 
* They will be able to add the menu items of their cafeteria and the chef details who will be handling all the orders in their office cafeteria.
 On the top right corner, the unique 4 digit code generated for this particular organization will be displayed. The code can be copied by clicking it. 
* 4 categories will be available to upload the menu items. Breakfast, lunch, dinner, and beverages/snacks are the categories available. While entering the menu items they need to provide the name, price, and a short description of that item. They will also upload an image of that item. They can upload the menu items in the respective category. 
* They will also be provided an option to edit and delete the particular menu item. By clicking a particular menu item they will be redirected to a page where the option to edit and delete will be available.
* They can add the chef details by clicking the button “Chef Details”. While entering the Chef details they will be providing the Chef’s name, email address, and password. They will also be able to delete a particular chef’s details. 


## _Employee Login_
* When a particular employee of a company logs into our application they need to provide his name, official email address, and the unique 4 digit code shared by the company. 
In the “Employee” login, the menu items available in the office cafeteria are displayed according to the category. 
* For each category, the orders will be available only for a stipulated amount of time. For breakfast, the orders will be taken only from 8 a.m. to 11 a.m. Similarly, for lunch, the timing will be 12 noon to 3 p.m, dinner from 7 p.m. to 10 p.m. But for the beverages and snacks, there will be no time limit.
* The employee will be provided two options while ordering the food. Either he can schedule the meal for later or order it immediately. By clicking the “Schedule order” button he can schedule a meal for later where he/she will be asked for a time in which they wish to order their meal. And by clicking the “Place order now” button the order will be placed immediately. 
* If the employee wishes to schedule all his/her meals for the day, they can click the schedule order button in all the categories. 
* All the orders that a particular person has scheduled will be available inside the “Cup symbol” available on the main screen. In that screen, they will be provided an option to delete a particular meal if needed. 
* As already mentioned each category will be available only for a particular time. If that time limit is passed, further orders will not be taken from that category of the menu. But during the time limit of each category both the options i.e., “Schedule order” and “Place order now” will be available.

## _Chef Login_
When the Chef logs into the application he needs to provide his/her official email address, password, and the unique 4 digit code. 
* In the “Chef” login, the scheduled orders of the employees will be displayed on the screen. 
* For each order, when the chef finishes preparing the food he/she will tick the check box provided right next to the order. Once the chef ticks the order, the corresponding employee will be getting a notification along with an alarm saying that his/her “Order is ready to take”. 
The employee can turn off the notification alarm either by clicking it and he will be redirected to a page with the “Dismiss” option. Or he can turn off the alarm by swiping it. This alarm indicates the order is readily available at the cafeteria counter.




## _Built with_

- [**Android Studio**](https://developer.android.com/docs) 
- [**Google Sheets API**](https://developers.google.com/sheets/api/quickstart/apps-script) 
- [**App Script**](https://developers.google.com/apps-script/reference/document) 
- [**Google Firebase Cloud Messaging**](https://firebase.google.com/docs/engage) 


## _User Guide_

![User_Guide](https://github.com/CodeFellas2021/Culinio/blob/master/User%20Manual.pdf)

## _Authors_

- [**_Shakthi A S_**](https://github.com/shakthi-26)
- [**_Sowmya V_**](https://github.com/vsowmyasv)
- [**_Harish Kumar R J_**](https://github.com/Harish-Kumar-R-J)
- [**_Gokul R_**](https://github.com/gokul2507)
