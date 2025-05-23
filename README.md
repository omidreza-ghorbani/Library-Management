# Library Management System

## Introduction

The Library Management System is a Java-based application that allows you to manage various library resources such as books, theses, treasure troves, and books for sale. The system supports multiple user roles (Admin, Manager, Student, Staff, and Professor) with distinct privileges and provides functionalities for resource borrowing, returning, purchasing, commenting, and generating detailed reports.

## Features

### User Management
- Supports different roles (Admin, Manager, Student, Staff, Professor) with role-specific access.

### Resource Management
- **Books:** Borrowable with copy tracking.
- **Theses:** Borrowable with advisor information.
- **Treasure Trove:** Special resources available only for on-site reading.
- **Books for Sale:** Purchaseable resources with discount calculations.

### Operations
- Borrowing and returning resources with penalty calculation for overdue returns.
- Purchasing resources with price adjustments based on discounts.
- Adding user comments to resources.

### Reporting and Logging
- Category and library reports.
- Overdue resources and penalty sum reports.
- Most popular resource report based on borrowing data.
- Sales report for purchased resources.
- Detailed logging of all major operations for audit and reporting purposes.

## Usage

The system uses a command-line interface where you can enter various text-based commands. Here are a few examples:

- **Add Library:**
  ```bash
  add-library#admin|AdminPass|library1
  
- **Add Category:**
    ```bash
  add-category#admin|AdminPass|category1|ignored|null
    
- **Add Student:**
    ```bash
    add-student#admin|AdminPass|student1|password|FirstName|LastName

- **Borrow:**
    ```bash
    borrow#student1|password|library1|book1|2025-04-04|12:00

## Project Documentation

- [Project Documentation (PDF) (in Persian)](./Library.pdf)

