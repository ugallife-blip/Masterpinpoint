# Master Pin Point — Android starter (v0.1.0)

This is the first native Android project scaffold for the agreed app.

## Included
- Dark trading-dashboard UI
- XAU/USD market panel and timeframe selector placeholders
- Signal panel with BUY / SELL / WAIT states (not yet connected to signal logic)
- Flexible balance and risk-percent calculator
- Auto-trading explicitly off; no broker login or order-placement code

## Important status
This is a source-code starter, not a compiled or tested APK. The live market feed and candlestick rendering are not connected yet. Do not treat placeholder labels as live prices or trade recommendations.

## Open
Open this folder in Android Studio (Giraffe or newer), allow Gradle sync, then run on an Android device/emulator.

## Next engineering steps
1. Verify a market-data provider's current API terms, endpoints, symbol format, update frequency, and historical candle access.
2. Implement a provider adapter and stale-feed/disconnect indicators.
3. Build candle aggregation and chart rendering for M1/M5/M15/H1/H4.
4. Add tested signal rules and explainable liquidity/support/resistance calculations.
5. Add contract-size, tick-value, leverage, and account-currency inputs before calculating lot size.
