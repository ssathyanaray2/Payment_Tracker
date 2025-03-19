# Payment_Tracker
 
# Overview 
Payment Tracker is a Android application designed to help users manage and track periodic payments. The app notifies users about due payments, securely stores related details, and provides insights into income and expenditure over a selected period.  

This project was developed as part of a **Software Engineering coursework**, following a structured approach that included **requirement analysis, system design, and documentation**. The repository contains:  
- **Software Requirement Specification (SRS)**  
- **Design Documents** (Class diagrams, Sequence diagrams, Use Case diagrams, etc.)  
- **Test Cases and System Architecture**  

## **Features**  
- **Set Payment Reminders:** Users can schedule periodic reminders for payments and receive notifications.  
- **Secure Data Storage:** Payment details, including related images (e.g., bonds, receipts), are stored securely in an encrypted format.  
- **Income & Expense Tracking:** Users can calculate gross income, expenditures, and remaining balance over a specific period.  
- **User Authentication:** Secure login system to protect user data.  
- **System Diagrams:** The repository includes class diagrams, sequence diagrams, and use case diagrams for a structured view of the system.  

## **Tech Stack**  
- **Frontend & Backend:** Java (Android)  
- **Database:** Firebase Realtime Database  
- **Cloud Services:** Firebase for authentication, data storage, and real-time sync   
- **Tools & Frameworks:**  
  - Android Studio (IDE)  
  - Git (Version Control)  

## **System Design & Documentation**  
The project follows standard software engineering principles, including:  
- **Requirement Analysis:** Documenting user needs and defining functional and non-functional requirements.  
- **System Architecture:** Implemented using a **client-server model** with Firebase as the backend.  
- **Process Model:** Developed using an **incremental approach**, ensuring iterative improvements.  
- **Control Style:** **Event-driven model**, where user interactions trigger system responses.  
- **Diagrams & Documentation:**  
  - **Class Diagrams** – Depicts the structure of the application.  
  - **Use Case Diagrams** – Shows interactions between users and the system.  
  - **Sequence Diagrams** – Represents the flow of operations.  
  - **State Diagrams** – Illustrates the state transitions of the system.  
  - **Test Cases Document** – Covers functional testing scenarios.  

## **Installation & Setup**  
1. Clone the repository:  
   git clone https://github.com/your-repo/payment-tracker.git
2. Open the project in Android Studio.  
3. Configure Firebase:  
   - Create a Firebase project.  
   - Enable authentication and Realtime Database.  
   - Add the google-services.json file to the project.  
4. Build and run the application on an emulator or a real device.  

## **Usage**  
- **Sign up or log in** to access the application.  
- **Set new reminders** with payment details.  
- **Receive notifications** for due payments.  
- **View and manage existing reminders** (edit or delete).  
- **Use the calculator** to track income and expenses over time.  

## **Future Enhancements**  
- Implement a **search bar** for quick access to reminders.  
- Add **tags and filters** to categorize reminders.  
- Integrate **investment plan suggestions** using ML-based recommendations.  
- Allow **advertisements** for financial services like banks and insurance providers.  

## Documentation  
For detailed requirements, design diagrams, and system architecture, refer to the [Payment Tracker Design Document](./Payment_Tracker.pdf).
