# Bitcoin-Price
Project for the module (Kotlin for java developers) \
On the website https://www.coindesk.com/coindesk-api several APIs are described to get information about Bitcoin.  The API endpoint https://api.coindesk.com/v1/bpi/currentprice.json returns the current Bitcoin Price in 3 different currencies as JSON string (see an example below). The prices are updated every minute. \

Implement a TornadoFX UI that shows the current prices for Bitoins. The Bitcoin prices must be retrieved online in an asynchronous way using Kotlin coroutines.

The Bitcoin prices in the UI should be updated automatically (in the background). The UI must stay reactive for user manipulations.

A history of changing prices is maintained and shown to the user. A history record contains at least the timestamp and the price of Bitcoins  in 3 currencies.
