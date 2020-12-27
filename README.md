# Bitcoin-Price
## Project for the module (Kotlin for java developers)
On the website https://www.coindesk.com/coindesk-api several APIs are described to get information about Bitcoin.  The API endpoint https://api.coindesk.com/v1/bpi/currentprice.json returns the current Bitcoin Price in 3 different currencies as JSON string (see an example below). The prices are updated every minute.

{
  "time": {
    "updated": "Nov 20, 2020 08:53:00 UTC",
    "updatedISO": "2020-11-20T08:53:00+00:00",
    "updateduk": "Nov 20, 2020 at 08:53 GMT"
  },
  "disclaimer": "This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org",
  "chartName": "Bitcoin",
  "bpi": {
    "USD": {
      "code": "USD",
      "symbol": "$",
      "rate": "18,281.1250",
      "description": "United States Dollar",
      "rate_float": 18281.125
    },
    "GBP": {
      "code": "GBP",
      "symbol": "£",
      "rate": "13,789.7085",
      "description": "British Pound Sterling",
      "rate_float": 13789.7085
    },
    "EUR": {
      "code": "EUR",
      "symbol": "€",
      "rate": "15,401.7016",
      "description": "Euro",
      "rate_float": 15401.7016
    }
  }
}
* Implement a TornadoFX UI that shows the current prices for Bitoins. The Bitcoin prices must be retrieved online in an asynchronous way using Kotlin coroutines. 

The Bitcoin prices in the UI should be updated automatically (in the background). The UI must stay reactive for user manipulations.

A history of changing prices is maintained and shown to the user. A history record contains at least the timestamp and the price of Bitcoins  in 3 currencies.
