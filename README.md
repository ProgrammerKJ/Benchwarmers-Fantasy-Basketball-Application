# Benchwarmers Fantasy Basketball Application 🏀

**A dedicated NBA fantasy platform built exclusively for basketball enthusiasts - developed in 4 intensive days as a capstone project.**

*HTD Cohort 6 - Group 6 "Exception Handlers"*  
*Team: Nicolas Perez, Kevin Sanchez, Krishna Joshi, Asim Shariff*

---

## 🎥 Complete Application Demo

### 📹 **Full Video Walkthrough**
**[🏀 Watch the Complete Demo Video](https://www.loom.com/share/8f17ad6e098e4ccda269257761c8e888?sid=05886f45-7992-410c-9632-fefc7964966d)**

*Experience the complete user journey from registration to active league participation! This comprehensive demo showcases all key features, including user authentication, admin league creation, player drafting mechanics, real-time leaderboards, and Sports Radar API integration - all built in just 4 intensive days.*

**Demo Highlights:**
- ✅ Complete user registration and role-based authentication
- ✅ Admin league creation and management capabilities  
- ✅ Real-time player drafting with availability constraints
- ✅ Fantasy team building and roster management
- ✅ Live leaderboards and performance tracking
- ✅ NBA team browsing and individual player statistics
- ✅ Sports Radar API integration for real-time data

---

## 🎯 Project Vision

Unlike major sports platforms that offer fantasy as just one small feature, **Benchwarmers** is built exclusively for NBA fantasy basketball. We created a streamlined, clutter-free experience that puts fantasy sports at the center of everything.

### The Problem We Solved
- **Cluttered Interfaces:** Existing platforms bury fantasy features among countless other options
- **Generic Experience:** One-size-fits-all approaches that don't cater to dedicated fantasy players  
- **Limited Focus:** Fantasy gets lost as a side feature rather than the main attraction

### Our Solution
A **dedicated fantasy-first platform** that eliminates distractions and delivers a personalized, engaging experience for serious NBA fantasy enthusiasts.

---

## ✨ Key Features

### 🔐 **Role-Based Authentication System**
- **JWT Security:** Secure user authentication and session management
- **Admin Controls:** League creation, management, and moderation privileges
- **User Profiles:** Customizable profiles with favorite teams and statistics

### 🏆 **Advanced League Management**
- **Multi-League Support:** Users can participate in multiple concurrent leagues
- **Admin-Controlled Creation:** Only admins can create and manage leagues
- **Dynamic Membership:** Add/remove users with real-time league updates
- **Leaderboard System:** Automated scoring and ranking based on real player performance

### 🎯 **Intelligent Player Drafting**
- **Availability Constraints:** Players become unavailable league-wide once drafted
- **Strategic Team Building:** Build balanced rosters across multiple positions
- **Real-Time Updates:** Instant roster changes and availability tracking
- **Smart Recommendations:** Player statistics help inform drafting decisions

### 📊 **Comprehensive NBA Data Integration**
- **Real-Time Player Stats:** Live performance metrics via Sports Radar API
- **Injury Reports:** Up-to-date player status and availability
- **Game Schedules:** Full NBA calendar with team matchups
- **Team Information:** Complete roster and performance data for all 30 NBA teams

### 🎮 **Interactive Fantasy Experience**
- **Team Management:** Add, remove, and swap players throughout the season
- **Performance Tracking:** Visual statistics and player performance charts
- **League Analytics:** Detailed scoring breakdowns and trend analysis

---

## 📸 Application Screenshots

### 🏠 **Landing Experience**

#### **Landing Page - Banner**
![Landing Page Banner](https://github.com/user-attachments/assets/dfdcaef8-99e4-403a-ac90-2bd71382ca7b)
*Clean, basketball-themed welcome banner with compelling call-to-action and clear navigation*

<br>
<br>
<br>

#### **Landing Page - Highlights**
![Landing Page Highlights](https://github.com/user-attachments/assets/a3a669f7-846d-4cf6-a9fc-44ffc9d4f779)
*Feature highlights showcasing the platform's unique fantasy-first approach and key capabilities*

<br>
<br>
<br>

---

### 🔐 **User Authentication**

#### **Signup Popup**
![Signup Modal](https://github.com/user-attachments/assets/19e1407e-757a-4b63-be01-41cc51230997)
*Streamlined user registration with TypeScript form validation and favorite team selection*

<br>
<br>
<br>

#### **Login Popup**
![Login Modal](https://github.com/user-attachments/assets/705cdd2d-7263-44fd-a276-1108fc04e05e)
*Secure JWT authentication with clean modal design and error handling*

<br>
<br>
<br>

---

### 🏆 **League Management**

#### **Join League Page**
![Join League Interface](https://github.com/user-attachments/assets/a4331f46-a267-48d4-9305-c0d4299051cc)
*Browse and join existing fantasy leagues with detailed league information and member counts*

<br>
<br>
<br>

#### **Create League Page**
![Create League Interface](https://github.com/user-attachments/assets/37d4116a-7d33-48e3-8f4e-98f006dcd8f7)
*Admin-only league creation with custom settings and season configuration*

<br>
<br>
<br>

#### **Fantasy League Dashboard**
![League Dashboard](https://github.com/user-attachments/assets/a2e9427c-fb3e-4fd9-9464-6b569f5f0d1b)
<br>
*Comprehensive league overview showing all members, their fantasy teams, and current fantasy points*

<br>
<br>
<br>

---

### 🎯 **Team Building & Player Management**

#### **Edit Fantasy Team Page**
![Team Builder Interface](https://github.com/user-attachments/assets/c2ee9867-29ab-4f99-ba0b-7a1bd82c9539)
*Strategic player selection with real-time availability tracking and position-based organization*

<br>
<br>
<br>

#### **Fantasy Top Performers Graph**
![Top Performers Chart](https://github.com/user-attachments/assets/4d463f85-c21c-407c-800f-e05f0820d453)
*Dynamic Chart.js visualization displaying top 25 fantasy league performers with real-time updates*

<br>
<br>
<br>

---

### 🏀 **NBA Data Integration**

#### **Team Section Page**
![All Teams Overview](https://github.com/user-attachments/assets/f525a523-9f61-4e56-82fa-e1db72d600ef)
*Complete NBA team directory with interactive logos leading to individual team pages*

<br>
<br>
<br>

#### **Individual Team Section Page**
![Team Details View](https://github.com/user-attachments/assets/bc310f22-3943-4a40-ba8e-5ee2a2988d23)
*Detailed team roster showing starting 5 players with comprehensive statistics and team information*

<br>
<br>
<br>

#### **Player Details Page**
![Player Statistics](https://github.com/user-attachments/assets/c28b0cc8-08ea-403f-99ef-471eb0dc3c6a)
*Live player statistics and performance metrics powered by Sports Radar API integration*

<br>
<br>
<br>

---

## 🛠️ Technical Architecture

### **Backend (Java + Spring Ecosystem)**
- **Spring Boot:** Robust RESTful API architecture
- **Spring Security + JWT:** Secure authentication and authorization
- **Spring JDBC:** Efficient database operations with custom repositories
- **MySQL:** Relational database with optimized schema design
- **Docker:** Containerized deployment for consistency across environments

### **Frontend (Modern React Stack)**
- **React 18:** Component-based UI with hooks and functional components
- **TypeScript:** Type-safe development with enhanced code reliability
- **Chart.js:** Dynamic data visualization for player statistics
- **Responsive Design:** Mobile-first approach with CSS Grid/Flexbox

### **External Integrations**
- **Sports Radar API:** Official NBA player statistics and game data
- **Swagger Sports API:** Supplementary team and player information

### **DevOps & Testing**
- **Docker Containerization:** Consistent development and deployment environments
- **Comprehensive Testing:** Unit tests for data and service layers
- **MVC Architecture:** Clean separation of concerns with proper layering

---

## 🏗️ System Architecture

```
Frontend (React + TypeScript)
        ↓
REST API (Spring Boot)
        ↓
Service Layer (Business Logic)
        ↓
Data Layer (JDBC + Repositories)
        ↓
MySQL Database
        ↓
Sports Radar API (External Data)
```

### **Database Design**
- **6 Core Entities:** Users, Leagues, Teams, Players, Games, Fantasy Teams
- **Complex Relationships:** Many-to-many associations between users, leagues, and players
- **Optimized Queries:** Custom repository patterns for efficient data retrieval

---

## 🚀 Installation & Setup

### **Prerequisites**
- Java 11 or higher
- Node.js 16+ and npm
- MySQL 8.0+
- Docker (recommended)

### **Quick Start with Docker**

1. **Clone the repository:**
```bash
git clone https://github.com/ProgrammerKJ/Benchwarmers-Fantasy-Basketball-Application.git
cd Benchwarmers-Fantasy-Basketball-Application
```

2. **Start the database:**
```bash
docker-compose up -d mysql
```

3. **Configure environment variables:**
Create `application.properties` in the backend:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fantasy_basketball
spring.datasource.username=your_username
spring.datasource.password=your_password
sports.radar.api.key=your_api_key
jwt.secret=your_jwt_secret
```

4. **Start the backend:**
```bash
cd server
./mvnw spring-boot:run
```

5. **Start the frontend:**
```bash
cd client
npm install
npm start
```

6. **Access the application:**
- Frontend: `http://localhost:3000`
- Backend API: `http://localhost:8080`

---

## 🎭 User Experience

### **For League Members:**
- **Team Building:** Draft and manage your fantasy roster
- **Performance Tracking:** Monitor your team's progress with real-time stats
- **League Competition:** Compete against friends with live leaderboards
- **Player Research:** Access comprehensive NBA player data and analytics

### **For League Admins:**
- **League Creation:** Set up new fantasy leagues with custom settings
- **Member Management:** Invite users and manage league participation
- **League Control:** Modify league settings and handle administrative tasks
- **Oversight Tools:** Monitor league activity and resolve disputes

---

## 🧠 Technical Challenges & Solutions

### **Complex Drafting Logic**
- **Challenge:** Ensuring players can only be drafted once per league while supporting multiple concurrent leagues
- **Solution:** Implemented sophisticated business logic in service layer with real-time availability tracking

### **Real-Time Data Integration**
- **Challenge:** Keeping NBA player statistics and injury reports current
- **Solution:** Efficient API integration with caching strategies and scheduled updates

### **Role-Based Security**
- **Challenge:** Implementing granular permissions for admin vs. user capabilities
- **Solution:** JWT-based authentication with Spring Security and custom authorization logic

### **4-Day Development Timeline**
- **Challenge:** Building a full-stack application with 4 developers in an extremely tight timeframe
- **Solution:** Agile development with clear role separation, continuous integration, and parallel development streams

---

## 👥 Team Collaboration

### **Backend Development** (Krishna Joshi + Asim Shariff)
- **Service Layer Architecture:** Implemented complex business logic and validation
- **Controller Development:** RESTful API endpoints with proper error handling  
- **Security Implementation:** JWT authentication and role-based authorization
- **Database Design:** Optimized schema with efficient relationship mapping

### **Frontend Development** (Nicolas Perez + Kevin Sanchez)
- **TypeScript Implementation:** Type-safe React components and state management
- **UI/UX Design:** Modern, responsive interface with intuitive user flows
- **API Integration:** Seamless communication with backend services

### **Cross-Functional Collaboration**
- **Design Integration:** Backend developers contributed to frontend styling and user experience
- **API Communication:** Collaborative development of frontend-backend integration
- **Testing & Debugging:** Team-wide effort in end-to-end testing and bug resolution

---

## 📊 Performance Metrics

- **Development Time:** 4 intensive days from concept to completion
- **Team Size:** 4 developers with cross-functional collaboration
- **Test Coverage:** Comprehensive unit testing for critical business logic
- **API Response Time:** Optimized queries for sub-200ms average response times
- **Architecture:** Scalable design supporting multiple concurrent leagues

---

## 🔮 Future Enhancements

- [ ] **Real-Time Chat:** League communication and social features
- [ ] **Mobile Application:** Native iOS/Android apps for on-the-go management
- [ ] **Advanced Analytics:** Machine learning predictions and player recommendations
- [ ] **Live Game Integration:** Real-time score updates during NBA games
- [ ] **Social Features:** Friend systems and cross-league interactions
- [ ] **Playoff System:** Automated tournament brackets and championship tracking

---

## 🏆 Technical Achievements

- **Rapid Development:** Full-stack application completed in 4 days
- **Complex Business Logic:** Sophisticated player drafting and league management systems
- **Modern Architecture:** Professional-grade Spring Boot backend with React frontend
- **Team Collaboration:** Successful coordination across frontend and backend development
- **Production-Ready:** Containerized deployment with proper security implementations

---

**Built with 🏀 by the Exception Handlers Team**  
*HTD Cohort 6 - Capstone Project*

**Krishna Joshi** - Backend Architecture & Services  
[LinkedIn](https://linkedin.com/in/krishnajoshi28) | [Portfolio](https://krishnasportfolio23.netlify.app)
