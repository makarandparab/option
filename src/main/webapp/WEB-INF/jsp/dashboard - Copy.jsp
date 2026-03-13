<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>



    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>OI Analyzr Pro | Nifty Live</title>
        <!-- Meta headers for cache control -->
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">
        <!-- Modern Typography -->
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=Outfit:wght@600;700&display=swap"
            rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/patternomaly/1.3.2/patternomaly.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-annotation@3.0.1"></script>
        <style>
            :root {
                --bg-color: #f0f2f5;
                --card-bg: rgba(255, 255, 255, 0.98);
                --primary-blue: #0066ff;
                --success-green: #10b981;
                --danger-red: #ef4444;
                --text-main: #1e293b;
                --text-muted: #64748b;
                --border-color: #e2e8f0;
                --shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.05), 0 4px 6px -2px rgba(0, 0, 0, 0.02);
            }

            body {
                font-family: 'Inter', sans-serif;
                background-color: var(--bg-color);
                color: var(--text-main);
                margin: 0;
                padding: 16px;
                display: flex;
                flex-direction: column;
                height: 100vh;
                width: 100vw;
                box-sizing: border-box;
                overflow: hidden;
            }

            header {
                width: 100%;
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 16px;
                flex-shrink: 0;
                animation: fadeIn 0.8s ease-out;
            }

            h1 {
                font-family: 'Outfit', sans-serif;
                font-size: 20px;
                margin: 0;
                background: linear-gradient(135deg, #0066ff, #00d2ff);
                background-clip: text;
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
            }

            .live-badge {
                display: flex;
                align-items: center;
                background: white;
                padding: 6px 12px;
                border-radius: 99px;
                box-shadow: var(--shadow);
                font-weight: 600;
                font-size: 13px;
            }

            .dot {
                height: 8px;
                width: 8px;
                background-color: var(--success-green);
                border-radius: 50%;
                display: inline-block;
                margin-right: 8px;
                animation: pulse 2s infinite;
            }

            .main-container {
                display: flex;
                flex-direction: column;
                gap: 16px;
                flex-grow: 1;
                min-height: 0;
                width: 100%;
            }

            @media (min-width: 1024px) {
                .main-container {
                    flex-direction: row;
                }
            }

            .card {
                background: var(--card-bg);
                border-radius: 12px;
                box-shadow: var(--shadow);
                padding: 20px;
                box-sizing: border-box;
                display: flex;
                flex-direction: column;
                min-height: 0;
                animation: slideUp 0.6s ease-out;
            }

            .card-chart {
                flex: 1.2;
            }

            .card-table {
                flex: 1;
            }

            .chart-container {
                position: relative;
                flex-grow: 1;
                min-height: 0;
                width: 100%;
            }

            .table-container {
                overflow-y: auto;
                flex-grow: 1;
                min-height: 0;
            }

            h2 {
                font-family: 'Outfit', sans-serif;
                font-size: 16px;
                margin-top: 0;
                margin-bottom: 12px;
                display: flex;
                align-items: center;
                gap: 8px;
                flex-shrink: 0;
            }

            table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0;
            }

            th {
                background: #f8fafc;
                padding: 10px 8px;
                font-size: 11px;
                font-weight: 600;
                color: var(--text-muted);
                text-transform: uppercase;
                letter-spacing: 0.025em;
                border-bottom: 1px solid var(--border-color);
                text-align: center;
                position: sticky;
                top: 0;
                z-index: 10;
            }

            td {
                padding: 10px 8px;
                font-size: 12px;
                border-bottom: 1px solid var(--border-color);
                text-align: center;
            }

            tbody tr:last-child td {
                border-bottom: none;
            }

            tbody tr:hover {
                background-color: #f1f5f9;
            }

            .signal-call {
                color: #065f46;
                background-color: #d1fae5;
                font-weight: 700;
                padding: 4px 12px;
                border-radius: 6px;
                font-size: 12px;
            }

            .signal-put {
                color: #991b1b;
                background-color: #fee2e2;
                font-weight: 700;
                padding: 4px 12px;
                border-radius: 6px;
                font-size: 12px;
            }

            @keyframes pulse {
                0% {
                    transform: scale(0.95);
                    box-shadow: 0 0 0 0 rgba(16, 185, 129, 0.7);
                }

                70% {
                    transform: scale(1);
                    box-shadow: 0 0 0 10px rgba(16, 185, 129, 0);
                }

                100% {
                    transform: scale(0.95);
                    box-shadow: 0 0 0 0 rgba(16, 185, 129, 0);
                }
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                }

                to {
                    opacity: 1;
                }
            }

            @keyframes slideUp {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }

                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
        </style>
    </head>

    <body>

        <header>
            <h1>OI Analyzr Pro</h1>
            <div class="live-badge">
                <% java.time.ZoneId indianZone=java.time.ZoneId.of("Asia/Kolkata"); java.time.ZonedDateTime
                    nowInIndia=java.time.ZonedDateTime.now(indianZone); java.time.format.DateTimeFormatter
                    formatter=java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"); String
                    formattedDateTime=nowInIndia.format(formatter); out.println("<p><strong>Current Time:</strong> " +
                    formattedDateTime + "</p>");
                    %>
            </div>

            <br>

            <div class="live-badge">Max Pain:
                <span id="maxPain" style="color: #ef4444; font-weight: 700;">-</span>
            </div>

            <div class="live-badge">
                <span class="dot"></span>
                Nifty: <span id="niftyPrice" style="margin-left:5px">Loading...</span>
            </div>
        </header>

        <main class="main-container">
            <div class="card card-chart">
                <h2>📊 Open Interest Analysis</h2>
                <div class="chart-container">
                    <canvas id="oiChart"></canvas>
                </div>
            </div>

            <div class="card card-table">
                <h2>⚡ Signal Intelligence</h2>
                <div class="table-container">
                    <table id="signalTable">
                        <thead>
                            <tr>
                                <th>Strike</th>
                                <th>PCR</th>
                                <th>eResist</th>
                                <th>eSupport</th>
                                <th>Call OI%</th>
                                <th>Put OI%</th>
                                <th>Call Vol</th>
                                <th>Put Vol</th>
                                <!--<th>Call Δ</th>-->
                                <!--<th>Put Δ</th>-->
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody id="signalBody"></tbody>
                    </table>
                </div>
            </div>
        </main>

        <!-- Inject Server Data -->
        <script>
            window.serverData = ${ jspDataJson };
            window.serverTrend = "${jspPriceTrend}";
        </script>

        <script>
            let myChart = null;

            function calculateRelativeChange(current, previous) {
                if (previous === undefined || previous === null || previous === 0) return 0;
                return ((current - previous) / Math.abs(previous)) * 100;
            }

            function safePrice(info) {
                if (info && info.current_price != null) return info.current_price.toFixed(2);
                return 'N/A';
            }

            function renderChart(labels, stablePut, changePut, changePutColors, stableCall, changeCall, changeCallColors, niftyPrice, customLabelColors, annotationX, targetValue) {
                const ctx = document.getElementById('oiChart').getContext('2d');

                if (myChart) {
                    myChart.destroy();
                }

                const annotations = {};
                if (annotationX !== null) {
                    annotations.line1 = {
                        type: 'line',
                        xMin: annotationX,
                        xMax: annotationX,
                        borderColor: 'rgba(0, 0, 0, 0.6)',
                        borderWidth: 2,
                        borderDash: [5, 5],
                        label: {
                            display: true,
                            content: 'Nifty: ' + targetValue,
                            position: 'start',
                            backgroundColor: 'rgba(0,0,0,0.7)',
                            color: 'white',
                            font: { size: 11 }
                        }
                    };
                }

                myChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [
                            {
                                label: 'Put OI (Stable)',
                                data: stablePut,
                                backgroundColor: 'rgba(16, 185, 129, 0.4)', // Green
                                borderColor: 'rgba(16, 185, 129, 1)',
                                borderWidth: 1,
                                stack: 'Stack 0',
                                barPercentage: 0.6,
                                categoryPercentage: 0.8
                            },
                            {
                                label: 'Put OI Change',
                                data: changePut,
                                backgroundColor: changePutColors,
                                borderColor: 'rgba(16, 185, 129, 1)',
                                borderWidth: 1,
                                stack: 'Stack 0',
                                barPercentage: 0.6,
                                categoryPercentage: 0.8
                            },
                            {
                                label: 'Call OI (Stable)',
                                data: stableCall,
                                backgroundColor: 'rgba(239, 68, 68, 0.4)', // Red
                                borderColor: 'rgba(239, 68, 68, 1)',
                                borderWidth: 1,
                                stack: 'Stack 1',
                                barPercentage: 0.6,
                                categoryPercentage: 0.8
                            },
                            {
                                label: 'Call OI Change',
                                data: changeCall,
                                backgroundColor: changeCallColors,
                                borderColor: 'rgba(239, 68, 68, 1)',
                                borderWidth: 1,
                                stack: 'Stack 1',
                                barPercentage: 0.6,
                                categoryPercentage: 0.8
                            }
                        ]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            x: {
                                grid: { display: false },
                                ticks: {
                                    color: customLabelColors,
                                    font: { weight: 'bold' }
                                }
                            },
                            y: {
                                beginAtZero: true,
                                grid: { borderDash: [2, 4], color: '#e2e8f0' }
                            }
                        },
                        plugins: {
                            legend: { position: 'top', labels: { usePointStyle: true, boxWidth: 8 } },
                            tooltip: {
                                mode: 'index',
                                intersect: false,
                                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                                titleColor: '#1e293b',
                                bodyColor: '#475569',
                                borderColor: '#e2e8f0',
                                borderWidth: 1,
                                padding: 10
                            },
                            annotation: {
                                annotations: annotations
                            }
                        },
                        interaction: {
                            mode: 'nearest',
                            axis: 'x',
                            intersect: false
                        }
                    }
                });
            }

            function renderPage() {
                try {
                    // Use injected data instead of fetch
                    if (!window.serverData) {
                        console.error("No server data found!");
                        return;
                    }

                    const data = window.serverData;
                    if (!data || !data.body || !data.body.overallData) {
                        console.warn("No data or incomplete data received from server");
                        document.getElementById('niftyPrice').innerText = 'Data Unavailable';
                        return;
                    }

                    const overallData = data.body.overallData;
                    const oiDataMap = data.body.oiData;
                    const niftyValue = data.niftyValue;
                    // Ensure niftyData is a number
                    const niftyData = parseFloat(data.niftyData);
                    const strikePrices = overallData.strikePriceList;
                    const maxPain = data.maxPainStrike || '-';

                    document.getElementById('niftyPrice').innerText = niftyValue || 'N/A';
                    document.getElementById('maxPain').innerText = maxPain;

                    // Use server-side trend
                    const priceTrend = window.serverTrend;

                    const stablePutOi = [];
                    const changePutOi = [];
                    const changePutColors = [];
                    const stableCallOi = [];
                    const changeCallOi = [];
                    const changeCallColors = [];
                    const customLabels = [];
                    const customLabelColors = [];

                    const signalBody = document.getElementById('signalBody');
                    signalBody.innerHTML = '';

                    // Check if patternomaly is available
                    const isPatternAvailable = typeof pattern !== 'undefined';

                    strikePrices.forEach(strike => {
                        const strikeKey = strike.toString();
                        if (oiDataMap[strikeKey]) {
                            const sData = oiDataMap[strikeKey];
                            const cInfo = sData.callInfo || {};
                            const pInfo = sData.putInfo || {};

                            // Newly added
                            const extremeResistance = sData.extremeResistance || 0;
                            const extremeSupport = sData.extremeSupport || 0;

                            const pOi = sData.putOi;
                            const pChange = sData.putOiChange;
                            const pChangeP = sData.putOiChangeP || 0;
                            if (pChange >= 0) {
                                stablePutOi.push(pOi - pChange);
                                changePutOi.push(pChange);
                                if (isPatternAvailable) {
                                    try {
                                        changePutColors.push(pattern.draw('diagonal-right-left', 'rgba(16, 185, 129, 0.8)'));
                                    } catch (e) {
                                        console.warn("Pattern draw failed, using solid color", e);
                                        changePutColors.push('rgba(16, 185, 129, 0.8)');
                                    }
                                } else {
                                    changePutColors.push('rgba(16, 185, 129, 0.8)');
                                }
                            } else {
                                stablePutOi.push(pOi);
                                changePutOi.push(Math.abs(pChange));
                                changePutColors.push('rgba(251, 191, 36, 0.9)');
                            }

                            const cOi = sData.callOi;
                            const cChange = sData.callOiChange;
                            const cChangeP = sData.callOiChangeP || 0;
                            if (cChange >= 0) {
                                stableCallOi.push(cOi - cChange);
                                changeCallOi.push(cChange);
                                if (isPatternAvailable) {
                                    try {
                                        changeCallColors.push(pattern.draw('diagonal-right-left', 'rgba(239, 68, 68, 0.8)'));
                                    } catch (e) {
                                        console.warn("Pattern draw failed, using solid color", e);
                                        changeCallColors.push('rgba(239, 68, 68, 0.8)');
                                    }
                                } else {
                                    changeCallColors.push('rgba(239, 68, 68, 0.8)');
                                }
                            } else {
                                stableCallOi.push(cOi);
                                changeCallOi.push(Math.abs(cChange));
                                changeCallColors.push('rgba(251, 191, 36, 0.9)');
                            }

                            const pcr = cOi > 0 ? (pOi / cOi) : 0;
                            let color = '#64748b';
                            if (pcr < 0.7) color = '#ef4444';
                            else if (pcr > 1.2) color = '#3b82f6';

                            // FIX: Replaced template literal with concatenation to avoid JSP EL conflict
                            customLabels.push('(' + strikeKey + ' : ' + pcr.toFixed(2) + ')');
                            customLabelColors.push(color);

                            let signal = "-";
                            let rowClass = "";

                            // Signal logic based on relative change of OI change percentages (5 mins & 15 mins)
                            const last5MinCallOiChgP = cInfo.last_5min_oi_change_percent || 0;
                            const last5MinPutOiChgP = pInfo.last_5min_oi_change_percent || 0;
                            const last15MinCallOiChgP = cInfo.last_15min_oi_change_percent || 0;
                            const last15MinPutOiChgP = pInfo.last_15min_oi_change_percent || 0;

                            // 5-min calculation
                            const relCChange5 = calculateRelativeChange(cChangeP, last5MinCallOiChgP);
                            const relPChange5 = calculateRelativeChange(pChangeP, last5MinPutOiChgP);

                            // 15-min calculation
                            const relCChange15 = calculateRelativeChange(cChangeP, last15MinCallOiChgP);
                            const relPChange15 = calculateRelativeChange(pChangeP, last15MinPutOiChgP);

                            let signals = [];

                            if (priceTrend === 'UP') {
                                // 5-min Rule: Call Rel Change <= -15% AND Put Rel Change [5%, 10%]
                                if (relCChange5 <= -15 && (relPChange5 >= 5 || relPChange5 <= 10)) {
                                    signals.push('5m: BUY CALL @ ' + safePrice(cInfo));
                                    rowClass = "signal-call";
                                }
                                // 15-min Rule: Same thresholds
                                if (relCChange15 <= -15 && (relPChange15 >= 5 || relPChange15 <= 10)) {
                                    signals.push('15m: BUY CALL @ ' + safePrice(cInfo));
                                    rowClass = "signal-call";
                                }

                                //Newly added - Puts Rel Change [10%, 15%] and Call Rel Change >= [-5%] and pcr is greater than 1.
                                if (relCChange5 >= -5 && (relPChange5 >= 10 || relPChange5 <= 15) && pcr > 1) {
                                    signals.push('5m: BUY CALL @ ' + safePrice(cInfo));
                                    rowClass = "signal-call";
                                }
                                //Newly added - Puts Rel Change [10%, 15%] and Call Rel Change >= [-5%] and pcr is greater than 1.
                                if (relCChange15 >= -5 && (relPChange15 >= 10 || relPChange15 <= 15) && pcr > 1) {
                                    signals.push('15m: BUY CALL @ ' + safePrice(cInfo));
                                    rowClass = "signal-call";
                                }

                                // if PCR gets unbalanced for 5 min
                                if (pcr >= 1.6 && (relPChange5 <= -10 && (relCChange5 >= 5 || relCChange5 >= 10))) {
                                    signals.push('PCR UB - 5m: BUY CALL @ ' + safePrice(pInfo));
                                    rowClass = "signal-put";
                                }
                                // if PCR gets unbalanced for 15 min
                                if (pcr >= 1.6 && (relPChange15 <= -10 && (relCChange15 >= 5 || relCChange15 >= 10))) {
                                    signals.push('PCR UB - 15m: BUY CALL @ ' + safePrice(pInfo));
                                    rowClass = "signal-put";
                                }
                            }
                            else if (priceTrend === 'DOWN') {
                                // 5-min Rule: Put Rel Change <= -15% AND Call Rel Change [5%, 10%]
                                if (relPChange5 <= -15 && (relCChange5 >= 5 || relCChange5 >= 10)) {
                                    signals.push('5m: BUY PUT @ ' + safePrice(pInfo));
                                    rowClass = "signal-put";
                                }
                                // 15-min Rule: Same thresholds
                                if (relPChange15 <= -15 && (relCChange15 >= 5 || relCChange15 >= 10)) {
                                    signals.push('15m: BUY PUT @ ' + safePrice(pInfo));
                                    rowClass = "signal-put";
                                }

                                //Newly added - Call Rel Change [10%, 15%] and Put Rel Change >= [-5%] and pcr is less than 1.
                                if ((relCChange5 >= 10 || relCChange5 >= 15) && relPChange5 >= -5 && pcr < 1) {
                                    signals.push('5m: BUY PUT @ ' + safePrice(pInfo));
                                    rowClass = "signal-put";
                                }
                                //Newly added - Call Rel Change [10%, 15%] and Put Rel Change >= [-5%] and pcr is less than 1.
                                if ((relCChange15 >= 10 || relCChange15 >= 15) && relPChange15 >= -5 && pcr < 1) {
                                    signals.push('15m: BUY PUT @ ' + safePrice(pInfo));
                                    rowClass = "signal-put";
                                }

                                // if PCR gets unbalanced for 5 min
                                if (pcr <= 0.6 && (relCChange5 <= -10 && (relPChange5 >= 5 || relPChange5 >= 10))) {
                                    signals.push('PCR UB - 5m: BUY PUT @ ' + safePrice(cInfo));
                                    rowClass = "signal-call";
                                }
                                // if PCR gets unbalanced for 15 min
                                if (pcr <= 0.6 && (relCChange15 <= -10 && (relPChange15 >= 5 || relPChange15 >= 10))) {
                                    signals.push('PCR UB - 15m: BUY PUT @ ' + safePrice(cInfo));
                                    rowClass = "signal-call";
                                }
                            }

                            if (signals.length > 0) {
                                signal = signals.join("<br>");
                            } else {
                                signal = "-";
                                rowClass = "";
                            }

                            // FIX: Replaced template literals for innerHTML with concatenation string construction
                            const row = '<tr>' +
                                '<td style="font-weight:600">' + strike + '</td>' +
                                '<td style="color:' + color + '; font-weight:700">' + pcr.toFixed(2) + '</td>' +
                                '<td style="color:red ; font-weight:700">' + extremeResistance.toLocaleString() + '</td>' +
                                '<td style="color:green ; font-weight:700">' + extremeSupport.toLocaleString() + '</td>' +
                                '<td style="color:' + (cChange >= 0 ? '#ef4444' : '#10b981') + '">' + cChangeP.toFixed(1) + '%</td>' +
                                '<td style="color:' + (pChange >= 0 ? '#10b981' : '#ef4444') + '">' + pChangeP.toFixed(1) + '%</td>' +
                                '<td>' + (cInfo.volume || 0).toLocaleString() + '</td>' +
                                '<td>' + (pInfo.volume || 0).toLocaleString() + '</td>' +
                                '<td><span class="' + rowClass + '">' + signal + '</span></td>' +
                                '</tr>';
                            signalBody.innerHTML += row;
                        } else {
                            stablePutOi.push(0); changePutOi.push(0); changePutColors.push('rgba(0,0,0,0)');
                            stableCallOi.push(0); changeCallOi.push(0); changeCallColors.push('rgba(0,0,0,0)');
                            customLabels.push(strikeKey);
                            customLabelColors.push('#64748b');
                        }
                    });

                    const targetValue = niftyData;
                    let annotationX = null;
                    if (!isNaN(targetValue)) {
                        for (let i = 0; i < strikePrices.length - 1; i++) {
                            if (strikePrices[i] <= targetValue && targetValue <= strikePrices[i + 1]) {
                                annotationX = i + (targetValue - strikePrices[i]) / (strikePrices[i + 1] - strikePrices[i]);
                                break;
                            }
                        }
                    }

                    renderChart(customLabels, stablePutOi, changePutOi, changePutColors,
                        stableCallOi, changeCallOi, changeCallColors, niftyValue, customLabelColors, annotationX, niftyData);

                } catch (error) {
                    console.error('Error rendering page:', error);
                    // Print stack trace for better debugging
                    if (error.stack) {
                        console.error(error.stack);
                    }
                }
            }

            function setupDynamicRefresh() {
                const now = new Date();
                const nowMs = now.getTime();

                // Trading Window: 09:05 to 16:00
                const startTotalMin = 9 * 60 + 5;
                const endTotalMin = 16 * 60;
                const currentTotalMin = now.getHours() * 60 + now.getMinutes();

                let nextRefreshMin;
                if (currentTotalMin < startTotalMin) {
                    nextRefreshMin = startTotalMin; // Wait for 9:05
                } else if (currentTotalMin < endTotalMin) {
                    // Find next 5-minute clock boundary (e.g. 9:10, 9:15)
                    nextRefreshMin = (Math.floor(currentTotalMin / 5) + 1) * 5;
                } else {
                    console.log("Trading session ended (after 16:00). Auto-refresh stopped.");
                    return;
                }

                if (nextRefreshMin > endTotalMin) return;

                const refreshDate = new Date(now);
                refreshDate.setHours(Math.floor(nextRefreshMin / 60), nextRefreshMin % 60, 0, 0);

                const delay = refreshDate.getTime() - nowMs;
                console.log("Sync Refresh: Scheduled at " + refreshDate.toLocaleTimeString() + " (" + Math.round(delay / 1000) + "s)");

                setTimeout(function () {
                    window.location.reload();
                }, delay);
            }

            // Initialization
            renderPage();
            setupDynamicRefresh();
        </script>
    </body>

    </html>