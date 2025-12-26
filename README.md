# VirtualDatapointGenerator

## Description
The **VirtualDatapointGenerator** is a Java application that generates **virtual datapoints** inside the **System Configuration Tool (SCT)**

*SCT documentation:*  
https://docs.johnsoncontrols.com/bas/r/Metasys/en-US/Metasys-System-Configuration-Guide/11.0/Metasys-system/Tools/System-Configuration-Tool-SCT

*GUI of the VirtualDatapointGenerator*  
![Tool User Interface](screenshots/GUI.png)

---

## The Issue
For a specific customer in HVAC industry, for each hardware temperature measure value, there have to be generated nine virtual datapoints:

- Limit LowLow
- Limit Low
- Limit High
- Limit HighHigh
- Alarm LowLow
- Alarm Low
- Alarm High
- Alarm HighHigh
- Fault  

Generating these datapoints manually (creating, naming and configuring them) can take at least one day depending on the size of the system.  

---

## The Solution
The **VirtualDatapointGenerator** automates the generation and also takes care of the naming and the correct set up of the virtual datapoints.

**It reads and writes XML files using the Document Object Model (DOM).**

How it works:
1. Export the folder containing the hardware datapoints and the destination folder for the virtual datapoints from the System Configuration Tool (SCT).
2. Select both folders in the VirtualDatapointGenerator.
3. Click "Process and Save" — the tool generates a .txt file containing the XML snippet.
4. Paste the content of the generated .txt file into the `export.xml` file inside the destination ZIP folder (replace/merge as required).
5. Save the ZIP and import the destination folder back into the System Configuration Tool (SCT). The virtual datapoints will be created.

**What used to take days now completes in minutes!**     

*Screenshot of an empty folder without the virtual datapoints*   
![Processed Excel file](screenshots/empty.png)
*Screenshot of a folder after the virtual datapoints were generated and imported back to the System Configuration Tool (SCT)*
![Successful import back to Jabot](screenshots/generated.png)

---

## Tools & Technologies
The project uses the following technologies and libraries:

- **Java 21** — main programming language  
- **Document Object Model (DOM)** — for reading and writing XML files  
- **JavaFX** — user interface  
- **Maven** — build and dependency management  
- **jlink / jpackage** — to generate a native Windows image  
- **ChatGPT** — used for CSS styling assistance

---

## Privacy / Notes
**For privacy and data-protection reasons some implementation details and workflows are omitted from this repository. If you have questions about the data or the workflow, feel free to contact me.**


