const apiBase = "http://localhost:8080/api/cars";
const authBase = "http://localhost:8080/api/auth";

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (token) {
        showCrudSection();
        fetchCars(); 
    }
});

document.getElementById("loginBtn").addEventListener("click", async () => {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const res = await fetch(`${authBase}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (!res.ok) throw new Error("Invalid login");

        const data = await res.json();
        localStorage.setItem("token", data.token);

        showCrudSection();
        fetchCars();
    } catch (err) {
        alert("⚠️ Login failed: " + err.message);
    }
});

document.getElementById("registerBtn").addEventListener("click", async () => {
    const username = document.getElementById("regUsername").value;
    const password = document.getElementById("regPassword").value;
    const confirm = document.getElementById("confirmPassword").value;

    if (password !== confirm) return alert("Passwords do not match!");

    try {
        const res = await fetch(`${authBase}/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (!res.ok) throw new Error("Registration failed");

        alert("✅ Registration successful. You can now log in.");
        toggleLogin();
    } catch (err) {
        alert("⚠️ " + err.message);
    }
});

function logout() {
    localStorage.removeItem("token");
    document.getElementById("crudSection").classList.add("hidden");
    document.getElementById("loginSection").classList.remove("hidden");
}

function toggleRegister() {
    document.getElementById("loginSection").classList.add("hidden");
    document.getElementById("registerSection").classList.remove("hidden");
}
function toggleLogin() {
    document.getElementById("registerSection").classList.add("hidden");
    document.getElementById("loginSection").classList.remove("hidden");
}
function showCrudSection() {
    document.getElementById("loginSection").classList.add("hidden");
    document.getElementById("registerSection").classList.add("hidden");
    document.getElementById("crudSection").classList.remove("hidden");
}

async function fetchCars() {
    const token = localStorage.getItem("token");
    if (!token) return;

    try {
        const res = await fetch(apiBase, {
            headers: { "Authorization": "Bearer " + token }
        });
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const cars = await res.json();

        const body = document.getElementById("carTableBody");
        body.innerHTML = "";
        let counter = 0;
        cars.forEach(car => {
            body.innerHTML += `
              <tr class="text-center">
                <td class="border p-2">${++counter}</td>
                <td class="border p-2">${car.licensePlateNumber}</td>
                <td class="border p-2">${car.make}</td>
                <td class="border p-2">${car.model}</td>
                <td class="border p-2">${car.year}</td>
                <td class="border p-2">${car.color}</td>
                <td class="border p-2">${car.bodyType}</td>
                <td class="border p-2">${car.engineType}</td>
                <td class="border p-2">${car.transmission}</td>
                <td class="border p-2">
                  <button onclick="openEditModal(${car.id}, '${car.licensePlateNumber}', '${car.make}', '${car.model}', ${car.year}, '${car.color}', '${car.bodyType}', '${car.engineType}', '${car.transmission}')" class="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600">Edit</button>
                  <button onclick="deleteCar(${car.id})" class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">Delete</button>
                </td>
              </tr>`;
        });
    } catch (err) {
        console.error("Fetch error:", err);
        alert("⚠️ Failed to load cars: " + err.message);
    }
}

function openCreateModal() {
    document.getElementById("carForm").reset();
    document.getElementById("carId").value = "";
    document.getElementById("modalTitle").innerText = "Add Car";
    document.getElementById("carModal").classList.remove("hidden");
}

function openEditModal(id, licensePlateNumber, make, model, year, color, bodyType, engineType, transmission) {
    document.getElementById("carId").value = id;
    document.getElementById("carLicensePlateNumber").value = licensePlateNumber;
    document.getElementById("carMake").value = make;
    document.getElementById("carModel").value = model;
    document.getElementById("carYear").value = year;
    document.getElementById("carColor").value = color;
    document.getElementById("carBodyType").value = bodyType;
    document.getElementById("carEngineType").value = engineType;
    document.getElementById("carTransmission").value = transmission;

    document.getElementById("modalTitle").innerText = "Edit Car";
    document.getElementById("carModal").classList.remove("hidden");
}

function closeModal() {
    document.getElementById("carModal").classList.add("hidden");
}

async function saveCar(e) {
    e.preventDefault();

    const id = document.getElementById("carId").value;
    const token = localStorage.getItem("token");
    const car = {
        licensePlateNumber: document.getElementById("carLicensePlateNumber").value,
        make: document.getElementById("carMake").value,
        model: document.getElementById("carModel").value,
        year: document.getElementById("carYear").value,
        color: document.getElementById("carColor").value,
        bodyType: document.getElementById("carBodyType").value,
        engineType: document.getElementById("carEngineType").value,
        transmission: document.getElementById("carTransmission").value
    };

    try {
        const method = id ? "PUT" : "POST";
        const url = id ? `${apiBase}/${id}` : apiBase;

        const res = await fetch(url, {
            method,
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(car)
        });

        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        closeModal();
        fetchCars();
    } catch (err) {
        alert("⚠️ Failed to save car: " + err.message);
    }
}

async function deleteCar(id) {
    if (!confirm("Delete this car?")) return;
    const token = localStorage.getItem("token");

    try {
        const res = await fetch(`${apiBase}/${id}`, {
            method: "DELETE",
            headers: { "Authorization": "Bearer " + token }
        });
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        fetchCars();
    } catch (err) {
        alert("⚠️ Failed to delete car: " + err.message);
    }
}