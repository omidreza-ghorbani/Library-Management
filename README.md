Library Management System



Introduction

The Library Management System is a Java-based application that allows you to manage various library resources such as books, theses, treasure troves, and books for sale. The system supports multiple user roles (Admin, Manager, Student, Staff, and Professor) with distinct privileges and provides functionalities for resource borrowing, returning, purchasing, commenting, and generating detailed reports.
Features

  User Management:

      Supports different roles (Admin, Manager, Student, Staff, Professor) with role-specific access.

  Resource Management:

      Books: Borrowable with copy tracking.

      Theses: Borrowable with advisor information.

      Treasure Trove: Special resources available only for on-site reading.

      Books for Sale: Purchaseable resources with discount calculations.

  Operations:

      Borrowing and returning resources with penalty calculation for overdue returns.

      Purchasing resources with price adjustments based on discounts.

      Adding user comments to resources.

  Reporting and Logging:

      Category and library reports.

      Overdue resources and penalty sum reports.

      Most popular resource report based on borrowing data.

      Sales report for purchased resources.

      Detailed logging of all major operations for audit and reporting purposes.

Installation and Setup
Prerequisites

    Java Development Kit (JDK): Version 8 or higher.

    Terminal/Command Prompt: For compiling and running the application.

Usage

The system uses a command-line interface where you can enter various text-based commands. Here are a few examples:

    Add Library:

add-library#admin|AdminPass|library1

Add Category:

add-category#admin|AdminPass|category1|ignored|null

Add Student:

add-student#admin|AdminPass|student1|password|FirstName|LastName

Borrow a Resource:

    borrow#student1|password|library1|book1|2025-04-04|12:00

For a complete list of commands and detailed usage instructions, please refer to the project documentation.
Project Documentation

For more in-depth information on the system design, command specifications, and additional usage details, please refer to the project documentation file provided with this repository:

  

Contributing

Contributions are welcome! If you would like to contribute to the project, please fork the repository and submit a pull request. Make sure your contributions adhere to the current coding style and include appropriate tests.
License

This project is licensed under the MIT License. See the LICENSE file for further details.
