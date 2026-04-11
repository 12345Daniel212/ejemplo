document.addEventListener('DOMContentLoaded', () => {

    // --- CONFIGURADOR DE PRODUCTO ---
    const sizeBtns = document.querySelectorAll('#size-options .btn-select');
    const roastBtns = document.querySelectorAll('#roast-options .btn-select');
    const grindCards = document.querySelectorAll('.molienda-card');
    const priceDisplay = document.getElementById('final-price');

    // Estado inicial corregido: 250g, Medio, Grano (Precio Retail base $150)
    let retailBase = 150, // Precio para 250g
        grindExtra = 0,    // Grano no cobra extra
        szVal = "250g",
        rstVal = "MEDIO",
        grdVal = "GRANO";

    function updateRetailPrice() {
        const total = retailBase + grindExtra;
        priceDisplay.innerText = total;
        // Actualizar etiqueta en vivo
        document.getElementById('display-size').innerText = szVal.toUpperCase();
        document.getElementById('display-roast').innerText = rstVal;
        document.getElementById('display-grind').innerText = grdVal;
    }

    // Lógica de Selección de Tamaño (Recuperada)
    sizeBtns.forEach(btn => btn.addEventListener('click', () => {
        document.querySelector('#size-options .btn-select.active').classList.remove('active');
        btn.classList.add('active');
        retailBase = parseInt(btn.dataset.price);
        szVal = btn.dataset.val;
        updateRetailPrice();
    }));

    // Lógica de Tueste
    roastBtns.forEach(btn => btn.addEventListener('click', () => {
        document.querySelector('#roast-options .btn-select.active').classList.remove('active');
        btn.classList.add('active');
        rstVal = btn.dataset.val.toUpperCase();
        updateRetailPrice();
    }));

    // Lógica de Molienda
    grindCards.forEach(card => card.addEventListener('click', () => {
        document.querySelector('.molienda-card.active').classList.remove('active');
        card.classList.add('active');
        grindExtra = parseInt(card.dataset.extra);
        grdVal = card.dataset.val.toUpperCase();
        updateRetailPrice();
    }));

    // Botón Ordenar (WhatsApp)
    document.getElementById('btn-order').addEventListener('click', () => {
        const finalPrice = retailBase + grindExtra;
        const msg = encodeURIComponent(`¡Hola Punto Tueste! Me interesa el pedido personalizado: Bolsa de ${szVal}, tueste ${rstVal}, molienda ${grdVal}. Total: $${finalPrice}`);
        window.open(`https://wa.me/523111026504?text=${msg}`);
    });


    // --- CALCULADORA DE MAYOREO (CORREGIDA: Inicia en 5kg) ---
    const slider = document.getElementById('b2b-range');
    const RETAIL_KG_REF = 340; // Referencia de precio menudeo por 1kg

    slider.addEventListener('input', (e) => {
        const kg = parseInt(e.target.value);
        let precioMayoreo;

        // Escala de precios mayoreo
        if(kg >= 30) precioMayoreo = 220;
        else if(kg >= 15) precioMayoreo = 245;
        else if(kg >= 10) precioMayoreo = 260;
        else precioMayoreo = 280; // Precio base para pedidos pequeños de mayoreo (5kg+)

        // Cálculo de ahorro vs menudeo ($340 x kg)
        const ahorro = (RETAIL_KG_REF - precioMayoreo) * kg;

        document.getElementById('kg-output').innerText = kg;
        document.getElementById('b2b-u-price').innerText = `$${precioMayoreo}`;
        document.getElementById('b2b-saving').innerText = `$${ahorro.toLocaleString()}`;
    });
});