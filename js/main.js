var apiUrl = 'http://localhost:8000/api';
var jwtToken = '';
var editingCarId = null;

document.getElementById('loginBtn').addEventListener('click', function() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    login(username, password);
});

function login(username, password) {
    fetch(`${apiUrl}/auth/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then(res => res.json())
    .then(data => {
        jwtToken = data.token;
        fetchCar();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Login failed');
    });
}

function fetchCar(){
    fetch(`${apiUrl}/cars`,{
        headers: {
            'Authorization': `Bearer ${jwtToken}`
        }
    })
    .then(res => res.json())
    .then(cars => {
        console.log(cars);
        const row = document.getElementById('tableBody');
        row.innerHTML = '';
        var counter = 0;
        cars.forEach(car => {
            row.innerHTML += `
            <tr class="text-center">
                <td>${++counter}</td>
                <td>${car.make}</td>
                <td>${car.model}</td>
                <td>${car.year}</td>
                <td>${car.licensePlateNumber}</td>
                <td>${car.color}</td>
                <td>${car.bodyType}</td>
                <td>${car.engineType}</td>
                <td>${car.transmission}</td>
                <td>
                <button onclick="deleteCar(${car.id})">Delete</button>
                <button onclick="editCar(${car.id})">Edit</button>
                </td>
            </tr>
        `;
        })
    })
    .catch(error => console.error(error));
}

function deleteCar(carId) {
    fetch(`${apiUrl}/cars/${carId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${jwtToken}`,
            'Content-Type': 'application/json'
        }
    })
    .then(() => fetchCar())
    .catch(error => console.error(error));
}

function openModal() {
    const modal = document.getElementById('formSection');
    modal.classList.remove('hidden');
    modal.querySelector("#modalTitle").innerText = "Add New Car";
}


function editCar(carId) {
    fetch(`${apiUrl}/cars/${carId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        }
    })
    .then(async res => {
        const contentType = res.headers.get("content-type");
        if (!res.ok) {
            const text = await res.text();
            alert("Error: " + text);
            return;
        }
        if (!contentType || !contentType.includes("application/json")) {
            const text = await res.text();
            alert("Non-JSON response: " + text);
            return;
        }
        const car = await res.json();
        editingCarId = carId;
        const modal = document.getElementById('formSection');
        modal.classList.remove('hidden');
        modal.querySelector("#modalTitle").innerText = "Edit Car";
        document.getElementById('make').value = car.make;
        document.getElementById('model').value = car.model;
        document.getElementById('year').value = car.year;
        document.getElementById('licensePlateNumber').value = car.licensePlateNumber;
        document.getElementById('color').value = car.color;
        document.getElementById('bodyType').value = car.bodyType;
        document.getElementById('engineType').value = car.engineType;
        document.getElementById('transmission').value = car.transmission;
    })
    .catch(error => console.error(error));
}

function saveCar(event) {
    event.preventDefault();
    const make = document.getElementById('make').value;
    const model = document.getElementById('model').value;
    const year = document.getElementById('year').value;
    const licensePlateNumber = document.getElementById('licensePlateNumber').value;
    const color = document.getElementById('color').value;
    const bodyType = document.getElementById('bodyType').value;
    const engineType = document.getElementById('engineType').value;
    const transmission = document.getElementById('transmission').value;
    const carData = {
        "make":make,
        "model":model,
        "year":year,
        "licensePlateNumber": licensePlateNumber,
        "color":color,
        "bodyType":bodyType,
        "engineType":engineType,
        "transmission":transmission
    };

    if (editingCarId) {
        fetch(`${apiUrl}/cars/${editingCarId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}` // <-- Add this line
            },
            body: JSON.stringify(carData)
        })
        .then(res => res.json())
        .then(res => {
            editingCarId = null;
            closeModal();
            fetchCar();
        })
        .catch(error => console.error('Error:', error));
    } else {
        fetch(`${apiUrl}/cars`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}` // <-- Add this line
            },
            body: JSON.stringify(carData)
        })
        .then(res => res.json())
        .then(res => {
            closeModal();
            fetchCar();
        })
        .catch(error => console.error('Error:', error));
    }
}

function closeModal() {
    const modal = document.getElementById('formSection');
    modal.classList.add('hidden');
    document.getElementById('carForm').reset();
}

window.editCar = editCar;