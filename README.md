# Smart Warehouse Automation System
This project describes a full automated smart warehouse system where intelligent vehicles handling boxes with different descriptions get received, stored and delivered..

## Overview

The Warehouse Automation System has been designed to streamline and optimize the management of storage and retrieval operations within an automated warehouse environment. This project centers on the efficient handling of storage boxes, the management of Autonomous Guided Vehicles (AGVs), and the maintenance of logs for robust auditing and monitoring. Emphasis has also been placed on concurrent and safe operation, including automated charging management for AGVs and seamless user interaction through a graphical application interface.

## System Features

- **Automated Storage and Retrieval:**  
  Storage and retrieval of boxes are performed automatically, with AGVs assigned to the required positions for pickup and drop-off tasks. The system allows boxes to be stored and retrieved efficiently using unique identifiers.

- **Autonomous Vehicle and Battery Management:**  
  AGVs continuously monitor their own battery levels. When a low battery is detected, the AGV is sent to a charging station. If a charging station is unavailable, the AGV is programmed to wait, and should a time-out occur, an error is raised. Synchronization mechanisms ensure that only one AGV can utilize a station at any moment.

- **Concurrent Access and Synchronization:**  
  Threading and synchronization have been implemented to guarantee safe concurrent operations, particularly during the allocation of charging stations and during synchronized storage and retrieval of goods.

- **Comprehensive Logging:**  
  Event logging is a core feature. Operations such as storage, retrieval, AGV movement, battery charging, and errors are logged both for users and for auditing purposes. The system also manages log files, enabling viewing, deletion, and moving/archiving of logs.

- **User Interface:**  
  A user-friendly interface was implemented using Java Swing. The interface provides fields for entering box details (ID, weight, description) and log management. Real-time system feedback is displayed within the UI, showcasing events like AGV movement, storage actions, and battery updates.

## System Architecture

The system is architectured with modular and clearly interacting components.  
**Key modules include:**
- **WarehouseAutomation:** Orchestrates system-wide processes, manages the AGV logs, storage system, charging stations, and overall event management.
- **StorageSystem and StorageArea:** Manages the logic for box storage, uniquely tracking each stored or retrieved item, and logging all events.
- **Box Component:** Represents the core entity for goods, containing properties such as ID, weight, and content, and supporting all relevant operations.
- **AGV and Battery Components:** AGVs are modeled with movement and box manipulation capabilities, alongside an internal battery object managing their energy state.
- **ChargingStation:** Controls the interaction between AGVs and station resources, ensuring charging is performed orderly and only one AGV charges at a time.
- **Process and LogManager:** Processes storage and retrieval requests, assigns AGVs, and logs all operations within the system for both user visibility and permanent auditing.

Class and component diagrams have been developed, mapping out these relationships and their interactions for maintainable and scalable growth in the future.[1]

## Technologies Used

- **Programming Language:** Java
- **UI Framework:** Java Swing
- **Multithreading and Synchronization:** Used for concurrent AGV and charging management
- **Logging and File Management:** For persistent audit trails and user logs

## Installation and Setup

1. **Prerequisites:**
   - Java JDK 8 or higher
   - Supported operating system for Java Swing applications

2. **Setup:**
   - Clone or download the repository.
   - Compile the Java source files using an IDE or command line.
   - Package and run the main class `WarehouseAutomation` to launch the system UI.

3. **Configuration:**
   - Log file paths and parameters (such as box locations, AGV numbers, and charging stations) may be configured within the setup.

## Usage

- **Storing Boxes:**  
  Input box ID, weight, and description into the UI and initiate the store command. The system assigns a free slot, moves an available AGV, and logs every stage of the process.

- **Retrieving Boxes:**  
  Provide a box ID and request retrieval. The system locates the box, dispatches an AGV if available and sufficiently charged, and logs the operation.

- **Log Interaction:**  
  Log files can be viewed, deleted, moved, or archived via the interface. Real-time system feedback is shown including AGV paths, storage events, and error/warning notifications.

## Implementation Details

### Core Classes and Behavior

- **StorageSystem & StorageArea:**  
  Responsible for tracking every box's entry and exit, updating counts and positions, and storing detailed logs of every operation.

- **AGV and Battery Logic:**  
  AGVs are modeled with identification, positional awareness, and activity states. Their batteries can be discharged through routine tasks and are recharged when connected to a station. Battery operations prevent negative charging and communicate warnings if thresholds are crossed.

- **ChargingStation Coordination:**  
  Charging station assignment is coordinated to permit only one AGV at a time, leveraging thread synchronization for fairness. Should a second AGV require charging, it must wait (up to 15 minutes) before either acquiring the station or causing a timeout exception.

### Threading and Concurrency

Multiple threads facilitate parallel and safe management of AGV actions, battery charging, and simultaneous storage/retrieval. Synchronized methods are used to avoid race conditions, especially surrounding charging station access and updating storage records.

### Error Handling

Exceptions are raised and logged in cases of failed retrievals, unavailable storage locations, charging timeouts, and invalid input. All errors are captured in the system logs for debugging and auditability.

## Testing

The system has been rigorously tested with targeted unit tests for every class and operation, including:
- **BatteryTest:** Battery initialization, charging/discharging, formatting, handling negative charge attempts, and error detection.
- **ChargingStationTest:** AGV assignment, resource exclusivity, battery charging, multi-AGV fairness.
- **StorageSystemTest:** Box counting, entry and exit tracking, event recording, and log safety.
- **ProcessTest:** Storage and retrieval flow, duplicate handling, exception safety, and AGV swapping.
- **LogManagerTest:** Log file creation, error resilience, and safe operations on files (creation, closing, moving).
- **AGVTest and BoxTest:** Movement, position, box pick-up and drop, and safe error handling for improper operations.
- **StorageAreaTest:** Box storage and retrieval, slot finding, invalid position management, and safe exception handling.

All unit tests have reported no failures or errors, demonstrating robust and resilient implementation.[1]

## Simulation

A simulation video scenario illustrates the full system in operation:
- Storing and retrieving boxes, AGV assignments, charging logic, and comprehensive log updates are performed live.
- The interface demonstrates stepwise progression from event triggering to physical/logical state updates across the system.

## Conclusion

This Warehouse Automation System demonstrates a highly modular, test-driven, and robustly architected application for modern warehouse management. By leveraging autonomous robotics, real-time logging, synchronized resource allocation, and a friendly GUI, it ensures safe, efficient, and audit-ready operations, meeting industry needs for reliability and transparency without referencing individual developer names.[1]

### Presentation Reference

[Project Presentation (PDF)](https://github.com/shawrajdeep00/Smart-Warehouse-System/blob/main/Warehouse%20Automation%20system-compressed.pdf)

