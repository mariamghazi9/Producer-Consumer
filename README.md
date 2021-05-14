# Producer-Consumer Simulation
### The program designed by CSED, Faculty of Engineering, Alexandria University students.
## Description 
Object-Oriented program developed in Java which simulates a production line using producer-consumer design pattern to target a critical synchronization issue where we need to achieve cooperation between different machines with no data loss, GUI is developed using JAVA FX

## UML![](https://lh6.googleusercontent.com/hm--pwzFy-pYWoV1GmJ5jkDNdKtmWLXHg3H5hp_dX5acLrsfT-KsWo-Hgh75s_P8vozcgZUP1mVuoSpipmdax-xSvfOXK3osFB1gMHCneSKXpD72mzU1lFxoR5I4bdWA6nWh191s)

## User Manual

### Adding Elements![](https://lh6.googleusercontent.com/a2wusZZHifakSod5I6XMCnX4fLq-hXNlwCyNMP37uTxHxka6RrAI1DlK_YLG3CIFQYT9qc2ISUBEKHdxkYygEj6OVw0YBflKCloFxtfGiEx2HxVWaQykfl1_lvjykTrA8Zkg5yQ4)

When you first open the simulator you will find the Add Queue button is selected where Add Queue and Add Machine buttons form a toggle group.

  
![](https://lh4.googleusercontent.com/iBkYtT1shQjUNJ-1YRTF6uOnt3DVVQYImJose_9Tm4WBINMuK-fNEaG7yFwlaXh185LSqfxpKDOm8lLNltn6nxZmBqfIobOsiyI9RX43dk7Cz2OgBmGfSWt7_abFNrzFzvJl7CGX)  
  
  
  
  
  

Then by double click on the canvas a new element is added to the specified location and its type depends on the selected button in the above toggle group.

This is an Example for adding a queue  
![](https://lh5.googleusercontent.com/bkXiwCFdmjNhQq4E_WUHUqcqi-NUJr7CUnWPPWtpEmMSP8p8jZWr03epOugR7YI1aGU_6ttKNNN5C8oRtu1OMKej_5b7XiE0zMNU67HPwRQ_OxoIaOoXhUDqHRIfdHs4FI577ZmV)

This is an Example for adding a machine.

You can move elements by drag and drop

### Adding Connection

In the top right of the screen we could find a connection toggle group too.

First we select either to add or delete a connection by clicking the suitable button.

After that to add connection we select the two end of the connection (Queue and Machine)

Where connection flow depends on the order in which we select the elements so if we select a queue then a machine the product flow will be from the queue to the machine.![](https://lh6.googleusercontent.com/nq8R72sojRYTSNkWiQN7_S88dbVQU7-FdqCEtvVNY5aMczdWgANuSynDaO6bbxkLq3lBjKP7p4cGOnt6cOwFeWLt02inxtgrhiZ17B-K2hjYlry6YADeVnIpnZx3o2_5UBQB5vBm)

While on selecting a machine then a queue then the queue becomes the machine destination then the product flow will be from the machine to the queue.

Here the product flows from the queue to the machine.
  
![](https://lh6.googleusercontent.com/FyGNis_U61eK7Gg85yl8Zn-6wT8-379PvvK3LaNocCVzSMa5NaM-k9IN97mAjXInETg2BwLdZaMgEDkYDQMZKisRKEYQ_ITOHm9C8duH68BhUWpki0LpR0gFnV0myNua71wAWMXa)

Here the product flows from the machine to the queue.

To delete a connection we first click its button in the toggle group and then select the connection in the same manner we add it, so to delete a specified connection we select its two ends in the right order mentioned before.

### Deleting Element

To delete elements we first should click the toggle button Delete Element then click on the element we wish to delete.

### Simulation
![](https://lh6.googleusercontent.com/5wCtzcRBhSgXwqzNEIwD56Ib_5zf_4ndeanRMcrLREuA4t_0kO0WHhTD9hGKyRB6SDJyLz0Hz1epe4xfycbZBzoRP58Rc2etDq-i_mkK33TfaEP41u1xpEMg88SALT_18oG86QQ5)

To run the simulation after constructing our elements we first check that the construction and all connections are valid then we type the number of products we wish to simulate then click play.

  
![](https://lh5.googleusercontent.com/XOdAZwCm1XSo4gJ97dmN7z-iflio55J0bbinBTM8XpqZjrzqqfHzLITPH1BmCZKuFF8u1UDp8vSRBsHzpHjzDw6L_t7Mz1RoSlwZHDFaQrfqRBAat06Ae5DTH_whv9OR0bX3SljY)  
  
Here the simulation is running.
  
### Replay Simulation

When the simulation is finished New Simulation and Replay Simulation buttons are shown.![](https://lh6.googleusercontent.com/pMMC-ozjhWcg3U_G2QiXO09ke-u4hjMgQWQLHrcuSfWCBg9j8o7mcNwjcs3IF0yFpBNbIumE-HWA-O1XYbFaLRh_a17W61PI518zNcMHB8Zu5JMEDrWi7TKWeZP9-BdS9wDhHYnb)

To replay a simulation we first choose its number from the choice box above the Replay Simulation button then we click Replay Simulation

If we wish to start a new construction for simulation we just click New Simulation.
