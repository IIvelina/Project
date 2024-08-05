function currencyChangeChecking(selectElement, index) {
    let selectedCurrency = selectElement.value;
    let amountInBGN = document.getElementById('priceCheckingInBGN-' + index).value;
    let priceSpan = document.getElementById('balanceChecking-' + index);

    fetch('/api/convert?' + new URLSearchParams({
        from: 'BGN',
        to: selectedCurrency,
        amount: amountInBGN
    }))
        .then(response => response.json())
        .then(data => {
            priceSpan.textContent = data.result + ' ' + selectedCurrency;
        })
        .catch(error => {
            console.log('An error occurred:' + error);
        });
}
