
const url = "http://localhost:8080";

var currentPage = 1;
var productMode = "allProducts";

function alertAddMes(message)
{
    $(".alert .alertMessage").append(message);
}

function closeAlert()
{
    $(".alert").hide('close');
}

function updateAllDiet()
{
    getAll("null",10,1,"null","breakfast",null);
    getAll("null",10,1,"null","lunch",null);
    getAll("null",10,1,"null","dinnerDay",null);
    getAll("null",10,1,"null","afternoon",null);
    getAll("null",10,1,"null","dinnerNight",null);
    getUsersReportsDate();
}

function getAll(table = null,limit = 10,page = 1,search=null,tablepart =null,mode=null) {

    if(tablepart != "null")
    {
        var date = document.getElementById("DTDS").value;
        var userName = JSON.parse(window.localStorage.getItem("user")).username;
        var table;
        fetch(url + `/user/productsDiet?username=${userName}&limit=${limit}&page=${page}&date=${date}&period=${tablepart}`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json','Authorization' : authHeader().Authorization}
            }
        ).then( response =>
            {
                if (response.status === 200)
                {
                    return response.json();
                }
                else
                {
                    throw "Ничего не найдено"
                }
            }
        ).then(pdata =>
            {
                var productsTable;
                switch(tablepart)
                {
                    case "breakfast":productsTable = document.getElementById("BDT");break;
                    case "lunch":productsTable = document.getElementById("LDT");break;
                    case "dinnerDay":productsTable = document.getElementById("DDDT");break;
                    case "afternoon":productsTable = document.getElementById("ADT");break;
                    case "dinnerNight":productsTable = document.getElementById("DNDT");break;
                }
                switch(tablepart)
                {
                    case "breakfast":table = "BDT";break;
                    case "lunch":table = "LDT";break;
                    case "dinnerDay":table="DDDT";break;
                    case "afternoon":table ="ADT";break;
                    case "dinnerNight":table = "DNDT";break;
                }
                productsTable.innerHTML = "";

                pdata.content.forEach(element => {
                    productsTable.innerHTML +=
                        `<td>${element.productName}</td> <td>${element.dayCalories}</td> <td>${element.dayProteins}</td>
                <td>${element.dayFats}</td><td>${element.dayCarbohydrates}</td>
                <td>
                    <button type="button" onclick="deleteR('${table}',${element.id})" class="btn btn-outline-dark rounded-0">Удалить</button>
                    <button type="button" onclick="edit('${table}',${element.id})" data-target="#UpdateDiet" data-toggle="modal"  class="btn btn-outline-dark rounded-0">Изменить</button>
                </td>`

                });

                makePagination("null",pdata,search,tablepart);
            }
        ).catch(message => {messageShow(message)});
    }
    else if(table == "HotelsCollection")
    {
        var idAdded = JSON.parse(localStorage.getItem('user')).id;

        fetch(url + `/user/productsUser?idAdded=${idAdded}&limit=${limit}&page=${page}`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json','Authorization' : authHeader().Authorization}
            }
        ).then( response =>
            {
                if (response.status === 200)
                {
                    return response.json();
                }
                else
                {
                    throw "Ничего не найдено"
                }
            }
        ).then(pdata =>
            {
                var productsTable = document.getElementById("ProductsUserCollection");
                productsTable.innerHTML = "";

                pdata.content.forEach(element => {
                    productsTable.innerHTML +=
                        `<td>${element.productName}</td> <td>${element.caloriesGram}</td> <td>${element.proteinsGram}</td>
            <td>${element.fatsGram}</td><td>${element.carbohydratesGram}</td>
            <td>
                <button type="button" onclick="deleteR('ProductsUserCollection',${element.id})" class="btn btn-outline-dark rounded-0">Удалить</button>
                <button type="button" onclick="edit('ProductsUserCollection',${element.id})" data-target="#UpdateProduct" data-toggle="modal"  class="btn btn-outline-dark rounded-0">Изменить</button>
            </td>`

                });
                makePagination("ProductsUserCollection",pdata,search,"null");
            }
        ).catch(message => {messageShow(message)});
    }
    else if (table == "UsersParamsCollection")
    {
        var id = JSON.parse(localStorage.getItem('user')).id;

        fetch(url + `/user/usersparams?idParams=${id}&limit=${limit}&page=${page}`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json','Authorization' : authHeader().Authorization}
            }
        ).then( response =>
            {
                if (response.status === 200)
                {
                    return response.json();
                }
                else
                {
                    throw "Ничего не найдено"
                }
            }
        ).then(pdata =>
            {
                var productsTable = document.getElementById("UsersParamsCollection");
                productsTable.innerHTML = "";

                pdata.content.forEach(element => {
                    productsTable.innerHTML +=
                        `<td>${element.userHeight}</td> <td>${element.userWeight}</td> <td>${(new Date(element.paramsDate)).toLocaleDateString()}</td>
                <td>
                    <button type="button" onclick="deleteR('UsersParamsCollection',${element.id})" class="btn btn-outline-dark rounded-0">Удалить</button>
                    <button type="button" onclick="edit('UsersParamsCollection',${element.id})" data-target="#UpdateParams" data-toggle="modal"  class="btn btn-outline-dark rounded-0">Изменить</button>
                </td>`

                });

                makePagination("UsersParamsCollection",pdata,search,"null");
            }
        ).catch(message => {messageShow(message)});
    }
    else if (table == "ProductsToAddCollection")
    {
        var productNameToSearch = document.getElementById("PTADProductName").value;
        var checkCategory = document.getElementById("DACNC").checked;
        if(checkCategory)
        {
            document.getElementById("DACN").disabled = false;
        }
        else
        {
            document.getElementById("DACN").disabled = true;
        }
        if(mode == "null")
        {
            mode = productMode;
        }
        var collection = document.getElementById("ProductsToAddCollection");
        limit = 12;
        if(mode == "usersProducts")
        {
            productMode = mode;
            var id = JSON.parse(localStorage.getItem("user")).id;
            fetch(url + `/user/products?limit=${limit}&page=${page}&productName=${productNameToSearch == ""? "null":productNameToSearch}&category=${checkCategory?document.getElementById("DACN").value:"null"}&idAdded=${id}`,
                {
                    method: 'GET',
                    headers: {'Content-Type': 'application/json', 'Authorization' : authHeader().Authorization}
                }
            ).then( response =>
                {
                    if (response.status === 200)
                    {
                        return response.json();
                    }
                    else
                    {
                        throw "Ничего не найдено"
                    }
                }
            ).then(pdata =>
                {
                    collection.innerHTML = "";
                    pdata.content.forEach(element => {
                        collection.innerHTML +=
                            `<div class="col-4 col-sm-6 col-md-3 col-lg-3 col-xl-3 my-1">
                        <div class="card ">
                          <div class="card-body">
                              <h5 class="card-title text-left">${element.productName}</h5>
                              <h6 class="card-subtitle mb-2 text-muted">${element.foodCategory}</h6>
                              <p class="card-text m-0">Калорий: ${element.caloriesGram} ккал</p>
                              <p class="card-text m-0">Белков: ${element.proteinsGram} грамм</p>
                              <p class="card-text m-0">Жиров: ${element.fatsGram} грамм</p>
                              <p class="card-text m-0">Углеводов: ${element.carbohydratesGram} грамм</p>
                              <div class="text-center"></div>
                              <button onclick="insertUsersDietProduct('${element.productName}')" class="btn btn-outline-dark m-0 ">Добавить</button>
                          </div>
                        </div>
                      </div>   `

                    });

                    makePagination("ProductsToAddCollection",pdata,search,"null");
                }
            ).catch(message => {messageShow(message)});
        }

        if(mode == "allProducts")
        {
            productMode = mode;
            fetch(url + `/user/products?limit=${limit}&page=${page}&productName=${productNameToSearch == ""? "null":productNameToSearch}&category=${checkCategory?document.getElementById("DACN").value:"null"}&idAdded=-1`,
                {
                    method: 'GET',
                    headers: {'Content-Type': 'application/json', 'Authorization' : authHeader().Authorization}
                }
            ).then( response =>
                {
                    if (response.status === 200)
                    {
                        return response.json();
                    }
                    else
                    {
                        throw "Ничего не найдено"
                    }
                }
            ).then(pdata =>
                {
                    collection.innerHTML = "";
                    pdata.content.forEach(element => {
                        collection.innerHTML +=
                            `<div class="col-12 col-sm-6 col-md-3 col-lg-3 col-xl-3 my-1">
                        <div class="card ">
                          <div class="card-body">
                              <h5 class="card-title text-left">${element.productName}</h5>
                              <h6 class="card-subtitle mb-2 text-muted">${element.foodCategory}</h6>
                              <p class="card-text m-0">Калорий: ${element.caloriesGram} ккал</p>
                              <p class="card-text m-0">Белков: ${element.proteinsGram} грамм</p>
                              <p class="card-text m-0">Жиров: ${element.fatsGram} грамм</p>
                              <p class="card-text m-0">Углеводов: ${element.carbohydratesGram} грамм</p>
                              <div class="text-center"></div>
                              <button onclick="insertUsersDietProduct('${element.productName}')" class="btn btn-outline-dark m-0 ">Добавить</button>
                          </div>
                        </div>
                      </div>   `

                    });

                    makePagination("ProductsToAddCollection",pdata,search,"null");
                }
            ).catch(message => {messageShow(message)});
        }
    }
}

function logOut()
{
    window.localStorage.removeItem("user");
    delete_cookie('username');
    delete_cookie('id');
    delete_cookie('token');
    delete_cookie('roles');
    window.location.href = "http://localhost:8080";
}

window.onload = function()
{
    var navLinks = document.getElementById('mySidenav');
    var actionButtons = document.getElementById('actionButtons');
    if(window.localStorage.getItem('user') == undefined)
    {
        actionButtons.innerHTML =`  
      <a href="/signIn" class=" text-decoration-none">
        <input type="button" value="Вход" class="btn btn-secondary border"/>
      </a>
      <a href="/signUp" class=" text-decoration-none">
        <input type="button" value="Регистрация" class="btn btn-secondary border"/>
      </a>`;

        navLinks.innerHTML += `
      <a href="javascript:void(0)" class="closebtn"  onclick="closeNav()">×</a>
      <h6><a href="/" class=" text-decoration-none">Главная страница</a></h6>`
    }
    else
    {
        var user= JSON.parse(window.localStorage.getItem("user"));
        if(user.roles[0] == "ROLE_USER")
        {
            actionButtons.innerHTML =
                `<p class="m-0">Привет,${user.username}</p>
          <div class="dropdown">
          <a class="btn btn-floating m-1 ripple-surface"
          id="dropdownMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
          role="button">
              <i class="far fa-user"></i>
          </a>
          <div class="dropdown-menu" aria-labelledby="dropdownMenu">
              <a href="/user" role="button" class=" dropdown-item text-decoration-none">Страница пользователя</a>
              <a onclick="logOut()" role="button" class=" dropdown-item text-decoration-none">Выход</a>
          </div>
          </div>`;
            navLinks.innerHTML += `
          <a href="javascript:void(0)" class="closebtn"  onclick="closeNav()">×</a>

          <h6><a href="/" class=" text-decoration-none">Главная страница</a></h6>
          <h6><a  href="/user" >Страница пользователя</a></h6>
          `
        }
        else if(user.roles[0] == "ROLE_ADMIN")
        {
            actionButtons.innerHTML = `
          <p class="m-0">Привет,${user.username} </p>
          <div class="dropdown">
          <a class="btn btn-floating m-1 ripple-surface"
          id="dropdownMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
          role="button">
              <i class="far fa-user"></i>
          </a>
          <div class="dropdown-menu" aria-labelledby="dropdownMenu">
              <a href="/user" role="button" class=" dropdown-item text-decoration-none">Страница пользователя</a>
              <a href="/admin" role="button" class=" dropdown-item text-decoration-none">Страница администратора</a>
              <a onclick="logOut()" role="button" class=" dropdown-item text-decoration-none">Выход</a>
          </div>
          </div>`;

            navLinks.innerHTML += `
          <a href="javascript:void(0)" class="closebtn"  onclick="closeNav()">×</a>
            <h6><a href="/" class=" text-decoration-none">Главная страница</a></h6>
            <h6><a  href="/user">Страница пользователя</a></h6>
            <h6><a  href="/admin">Страница администратора</a></h6>
            `
        }
        var date = new Date();
        var curr_date = date.getDate();
        var curr_month = date.getMonth() + 1;
        var curr_year = date.getFullYear();
        var date_format = curr_year + "-" + (curr_month < 10? '0' + curr_month: curr_month) + "-" + (curr_date < 10? '0' + curr_date: curr_date);
        document.getElementById("DTDS").value = date_format;
        document.getElementById("AUPD").value = date_format;
        document.getElementById("AUDD").value = date_format;
        updateAllDiet();
        getCategories();
    }
};

function messageShow(message)
{
    $(".alert .alertMessage").text("");
    alertAddMes(message);
    $(".alert").show('close');
    $(".alert").alert();
}

function getCategories()
{

    fetch(url + '/user/foodcategories_products',
        {
            method: 'GET',
            headers: {'Content-Type': 'application/json', "Authorization" : authHeader().Authorization}
        }
    ).then((response) =>
        {
            return response.json();
        }
    ).then((pdata)=>
    {
        let UPDATE_USERPRODUCT_CATEGORY = document.getElementById("UPDATE_USERPRODUCT_CATEGORY");
        let DACN =document.getElementById("DACN");
        let PTACategoryName =document.getElementById("PTACategoryName");
        pdata.forEach(element => {
            UPDATE_USERPRODUCT_CATEGORY.innerHTML += `<option value="${element.categoryName}">${element.categoryName}</option>`;
            DACN.innerHTML += `<option value="${element.categoryName}">${element.categoryName}</option>`;
            PTACategoryName.innerHTML += `<option value="${element.categoryName}">${element.categoryName}</option>`;
        })
    });
}

function insert(table) {
    var showAlert = false;
    switch(table)
    {
        case "ProductsUserCollection":
        {
            let productName = document.getElementById("PTAProductName").value;
            let caloriesGram = document.getElementById("PTACaloriesGram").value;
            let proteinsGram = document.getElementById("PTAProteinsGram").value;
            let carbohydratesGram = document.getElementById("PTACarbohydratesGram").value;
            let fatsGram = document.getElementById("PTAFatsGram").value;
            let foodCategory = document.getElementById("PTACategoryName").value;

            $(".alert .alertMessage").text("");
            if(productName.length < 1  || productName.length >  200) {alertAddMes("Название продукта: 1-200 символов."); showAlert = true;}
            if(caloriesGram <= 0  || caloriesGram >  1000) {alertAddMes("Количество калорий на 100 грамм: 0-1000 единиц."); showAlert = true;}
            if(proteinsGram <= 0  || proteinsGram >  100) {alertAddMes("Количество белков на 100 грамм: 0-100 единиц."); showAlert = true;}
            if(carbohydratesGram <= 0  || carbohydratesGram >  100) {alertAddMes("Количество углеводов на 100 грамм: 0-100 единиц."); showAlert = true;}
            if(fatsGram <= 0  || fatsGram >  100) {alertAddMes("Количество жиров на 100 грамм: 0-100 единиц."); showAlert = true;}
            if(foodCategory.length == 0) {alertAddMes("Поле 'Название категории' не заполнено"); showAlert = true;}


            if(showAlert)
            {
                $(".alert").show('close');
                $(".alert").alert();
                return false;
            }
            closeAlert();
            fetch(url + '/user/products',
                {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json', 'Authorization' : authHeader().Authorization},
                    body: JSON.stringify(
                        {
                            idAdded :JSON.parse(localStorage.getItem("user")).id,
                            productName : productName,
                            caloriesGram : caloriesGram,
                            proteinsGram : proteinsGram,
                            fatsGram : fatsGram,
                            carbohydratesGram : carbohydratesGram,
                            foodCategory : foodCategory
                        }
                    )
                }).then( response =>
                {
                    if (response.status === 200)
                    {
                        messageShow("Продукт добавлен успешно");
                        getAll(table,limit = 10,currentPage,search=null,"null",productMode)
                        return;
                    }
                    else
                    {
                        throw "Выберите другие значения полей. Продукт не удалось добавить в коллекцию."
                    }
                }
            ).catch(message => {messageShow(message)})
            break;
        }
        case "UsersParamsCollection":
        {
            let userHeight =document.getElementById("UPTAUserHeight").value;
            let userWeight =document.getElementById("UPTAUserWeight").value;
            let reportDate =document.getElementById("AUPD").value;
            $(".alert .alertMessage").text("");

            if(Date.parse(reportDate) > Date.now()) {alertAddMes("Неверная дата."); showAlert = true;}
            if(userHeight < 30  || userHeight>  300) {alertAddMes("Рост: 30-300 единиц."); showAlert = true;}
            if(userWeight < 10  || userWeight>  300) {alertAddMes("Вес: 10-300 единиц."); showAlert = true;}

            if(showAlert)
            {
                $(".alert").show('close');
                $(".alert").alert();
                return false;
            }
            closeAlert();
            fetch(url + '/user/usersparams',
                {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json', 'Authorization' : authHeader().Authorization},
                    body: JSON.stringify(
                        {
                            idParams : JSON.parse(localStorage.getItem("user")).id,
                            paramsDate: reportDate,
                            userHeight : userHeight,
                            userWeight : userWeight
                        }
                    )
                }).then( response =>
                {
                    if (response.status === 200)
                    {
                        messageShow("Запись добавлена успешно");
                        getAll(table,limit = 10,currentPage,"null","null",productMode)
                        return;
                    }
                    else
                    {
                        throw "Введённые значения нарушают ограничения целостности таблиц(ы). Выберите другие значения полей"
                    }
                }
            ).catch(message => {messageShow(message)})
            break;
        }

    }
}

function insertUsersDietProduct(productName) {
    var showAlert = false;
    var grams = document.getElementById("DAG").value;
    var eatPeriod = document.getElementById("DAEP").value;
    var reportDate = document.getElementById("AUDD").value;
    var idReport = JSON.parse(localStorage.getItem('user')).id;

    $(".alert .alertMessage").text("");
    if(productName.length < 1  || productName.length >  200) {alertAddMes("Название продукта: 1-200 символов."); showAlert = true;}
    if(grams <= 0  || grams >  10000) {alertAddMes("Количество грамм: 1-10000 единиц."); showAlert = true;}
    if(Date.parse(reportDate) > Date.now() || reportDate == "") {alertAddMes("Неверная дата."); showAlert = true;}


    if(showAlert)
    {
        $(".alert").show('close');
        $(".alert").alert();
        return false;
    }
    closeAlert();
    fetch(url + '/user/reports',
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Authorization' : authHeader().Authorization},
            body: JSON.stringify(
                {
                    idReport : idReport,
                    productName : productName,
                    grams : grams,
                    eatPeriod : eatPeriod,
                    reportDate : reportDate
                }
            )
        }).then( response =>
        {
            if (response.status === 200)
            {
                messageShow("Продукт добавлен успешно");
                updateAllDiet();
                return;
            }
            else
            {
                throw "Выберите другие значения полей."
            }
        }
    ).catch(message => {messageShow(message)});
}

function makePagination(table = null,pdata,search=null,tablepart = null)
{
    var Pagination;
    currentPage = pdata.currentPage;
    if(table != "null")
    {
        switch(table)
        {
            case "ProductsToAddCollection":Pagination = document.getElementById("AUDP"); break;
            case "ProductsUserCollection":Pagination = document.getElementById("PUCTP"); break;
            case "UsersParamsCollection":Pagination = document.getElementById("UPCTP"); break;
        }
    }
    else if(tablepart != "null")
    {
        switch(tablepart)
        {
            case "breakfast":Pagination = document.getElementById("BDTP");break;
            case "lunch":Pagination = document.getElementById("LDTP");break;
            case "dinnerDay":Pagination = document.getElementById("DDDTP");break;
            case "afternoon":Pagination = document.getElementById("ADTP");break;
            case "dinnerNight":Pagination = document.getElementById("DNDTP");break;
        }
    }
    Pagination.innerHTML = "";
    if(pdata.currentPage == 1)
    {
        Pagination.innerHTML +=
            `
        <li class="page-item disabled">
            <a class="page-link"  tabindex="-1">Предыдущая</a>
        </li>
        `
    }
    else
    {
        Pagination.innerHTML +=
            `
        <li class="page-item">
            <a class="page-link" onclick="getAll('${table}',10,${pdata.currentPage - 1},'${search}','${tablepart}','${productMode}') " tabindex="-1">Предыдущая</a>
        </li>
        `
    }


    for(i = 1; i <= 3 && i <= pdata.totalPages;i++)
    {
        if(i == pdata.currentPage)
        {
            continue;
        }
        else if ( pdata.currentPage >= 6)
        {
            Pagination.innerHTML +=
                `
            <li class="page-item">
                <a class="page-link" onclick="getAll('${table}',10,${i},'${search}','${tablepart}','${productMode}')">${i}<span class="sr-only">(current)</span></a>
            </li>
            `
        }
    }
    for(i = (pdata.currentPage - 2 >= 1? pdata.currentPage - 1 : 1); i <= pdata.currentPage + 3 && i <= pdata.totalPages;i++)
    {
        if(i == pdata.currentPage - 1 && pdata.totalPages >= 5 && i >= 2)
        {
            Pagination.innerHTML +=
                `
            <li class="page-item">
                <a class="page-link" onclick="getAll('${table}',10,${i - 1},'${search}','${tablepart}','${productMode}')">...<span class="sr-only">(current)</span></a>
            </li>
            `
        }
        if(i == pdata.currentPage)
        {
            Pagination.innerHTML +=
                `
            <li class="page-item active">
                <a class="page-link">${pdata.currentPage}<span class="sr-only">(current)</span></a>
            </li>
            `
        }
        else
        {
            Pagination.innerHTML +=
                `
            <li class="page-item">
                <a class="page-link" onclick="getAll('${table}',10,${i},'${search}','${tablepart}','${productMode}')">${i}<span class="sr-only">(current)</span></a>
            </li>
            `
        }
        if(i == pdata.currentPage + 1 && pdata.totalPages >= 5 && i <= pdata.totalPages - 2)
        {
            Pagination.innerHTML +=
                `
            <li class="page-item">
                <a class="page-link" onclick="getAll('${table}',10,${i + 1},'${search}','${tablepart}','${productMode}')">...<span class="sr-only">(current)</span></a>
            </li>
            `
            break;
        }
    }

    for(i = pdata.totalPages - 2;i <= pdata.totalPages;i++)
    {
        if(i <= pdata.currentPage + 2)
        {
            continue;
        }
        else
        {
            Pagination.innerHTML +=
                `
            <li class="page-item">
                <a class="page-link" onclick="getAll('${table}',10,${i},'${search}','${tablepart}','${productMode}')">${i}<span class="sr-only">(current)</span></a>
            </li>
            `
        }
    }

    if(pdata.currentPage == pdata.totalPages)
    {
        Pagination.innerHTML +=
            `
        <li class="page-item disabled">
            <a class="page-link" >Следующая</a>
        </li>
        `
    }
    else
    {
        Pagination.innerHTML +=
            `
        <li class="page-item">
            <a class="page-link" onclick="getAll('${table}',10,${pdata.currentPage + 1},'${search}','${tablepart}','${productMode}')">Следующая</a>
        </li>
        `
    }
}

function deleteR(table,id)
{
    var finalUrl = url;
    if(table == "BDT" || table == "LDT" ||table == "DDDT" ||table == "ADT" ||table == "DNDT")
    {
        finalUrl += `/user/reports/${id}`;
    }
    else if(table == "ProductsUserCollection")
    {
        finalUrl += `/user/products/${id}`;
    }
    else if (table == "UsersParamsCollection")
    {    var userId = JSON.parse(localStorage.getItem('user')).id;
        finalUrl += `/user/usersparams/${userId}/${id}`;
    }
    if(table != "UsersParamsCollection")
    {
        fetch(finalUrl,
            {
                method: 'DELETE',
                headers: {'Content-Type': 'application/json','Authorization' : authHeader().Authorization}
            }
        ).then( response =>
        {
            if (response.status === 200)
            {
                if(table == "BDT" || table == "LDT" ||table == "DDDT" ||table == "ADT" ||table == "DNDT")
                {
                    updateAllDiet();
                }
                else if(table == "ProductsUserCollection")
                {
                    getAll('ProductsUserCollection',10,1,'null','null');
                }

                else
                {
                    throw "Ошибка удаления."
                }
            }
        }).catch(message => {messageShow(message)});
    }
    else
    {
        fetch(finalUrl,
            {
                method: 'DELETE',
                headers: {'Content-Type': 'application/json','Authorization' : authHeader().Authorization}
            }
        ).then( response =>
        {
            if (response.status === 200)
            {
                getAll('UsersParamsCollection',10,1,'null','null');
            }
            else
            {
                if(response.headers.get('ErrorMessage') == "Last users params")
                    throw "Нельзя удалить единственную запись параметров пользователя.";
            }

        }).catch(message => {messageShow(message)});
    };
}

function getUsersReportsDate()
{
    var date = document.getElementById('DTDS').value;
    var userId = JSON.parse(localStorage.getItem('user')).id;
    fetch(url + `/user/reports?id=${userId}&date=${date}`,
        {
            method: 'GET',
            headers: {'Content-Type': 'application/json','Authorization' : authHeader().Authorization}
        }
    ).then( response =>
        {
            if (response.status === 200)
            {
                return response.json();
            }
            else
            {
                throw "Ничего не найдено"
            }
        }
    ).then(pdata =>
        {
            var nutrientsNeeded = document.getElementById("NUTNED");
            nutrientsNeeded.innerHTML = "";
            nutrientsNeeded.innerHTML +=
                `<tr><td></td><td>Необходимо</td> <td>Получено</td></tr>
            <tr><td>Калорий</td><td>${pdata.calsNeeded} ккал</td> <td>${pdata.calsEaten} ккал</td></tr>
            <tr><td>Белков</td><td>${pdata.proteinsNeeded} г.</td> <td>${pdata.proteinsEaten} г.</td></tr>
            <tr><td>Жиров</td><td>${pdata.fatsNeeded} г.</td> <td>${pdata.fatsEaten} г.</td></tr>
            <tr><td>Углеводов</td><td>${pdata.carbohydratesNeeded} г.</td> <td>${pdata.carbohydratesEaten} г.</td></tr>`

        }
    ).catch(message => {messageShow(message)});
}

function update(table)
{
    var finalUrl = url;
    var showAlert = false;
    var body;
    if(table == "DietTable")
    {
        var id = document.getElementById("REPORTID").value;

        finalUrl += `/user/reports/${id}`;

        var reportDate = document.getElementById("UPDATE_DIET_DATE").value;
        var dayGram = document.getElementById("GRAMSUPDATE").value;
        $(".alert .alertMessage").text("");
        if(dayGram <= 0  || dayGram >  10000) {alertAddMes("Количество грамм: 1-10000 единиц."); showAlert = true;}
        if(Date.parse(reportDate) > Date.now()) {alertAddMes("Неверная дата."); showAlert = true;}
        if(!Number.isInteger(Number.parseInt(id))) {alertAddMes("Перезагрузите страницу. Неверное значение идентификтора записи."); showAlert = true;}

        if(showAlert)
        {
            $(".alert").show('close');
            $(".alert").alert();
            return false;
        }

        closeAlert();
        body = { dayGram : dayGram,
            reportDate:reportDate};
    }
    else if(table == "ProductsUserCollection")
    {
        var id = document.getElementById("PRODUCTID").value;
        finalUrl += `/user/products/${id}`;
        var productName = document.getElementById("UPDATE_USERPRODUCT_PRODUCTNAME").value;
        var caloriesGram = document.getElementById("UPDATE_USERPRODUCT_CALORIESGRAM").value;
        var proteinsGram = document.getElementById("UPDATE_USERPRODUCT_PROTEINSGRAM").value;
        var carbohydratesGram = document.getElementById("UPDATE_USERPRODUCT_CARBSGRAM").value;
        var foodCategory = document.getElementById("UPDATE_USERPRODUCT_CATEGORY").value;
        var fatsGram = document.getElementById("UPDATE_USERPRODUCT_FATSGRAM").value;

        $(".alert .alertMessage").text("");
        if(productName.length < 1  || productName.length >  200) {alertAddMes("Название продукта: 1-200 символов."); showAlert = true;}
        if(caloriesGram <= 0  || caloriesGram >  1000) {alertAddMes("Количество калорий на 100 грамм: 0-1000 единиц."); showAlert = true;}
        if(proteinsGram <= 0  || proteinsGram >  100) {alertAddMes("Количество белков на 100 грамм: 0-100 единиц."); showAlert = true;}
        if(carbohydratesGram <= 0  || carbohydratesGram >  100) {alertAddMes("Количество углеводов на 100 грамм: 0-100 единиц."); showAlert = true;}
        if(fatsGram <= 0  || fatsGram >  100) {alertAddMes("Количество жиров на 100 грамм: 0-100 единиц."); showAlert = true;}
        if(foodCategory.length == 0) {alertAddMes("Поле 'Название категории' не заполнено"); showAlert = true;}
        if(!Number.isInteger(Number.parseInt(id))) {alertAddMes("Перезагрузите страницу. Неверное значение идентификтора записи."); showAlert = true;}


        if(showAlert)
        {
            $(".alert").show('close');
            $(".alert").alert();
            return false;
        }
        closeAlert();
        body =
            {
                productName:productName,
                caloriesGram:caloriesGram,
                proteinsGram:proteinsGram,
                carbohydratesGram:carbohydratesGram,
                fatsGram:carbohydratesGram,
                foodCategory:foodCategory
            }
    }
    else if (table == "UsersParamsCollection")
    {
        var id = document.getElementById("PARAMSID").value;
        finalUrl += `/user/usersparams/${id}`;
        var userHeight = document.getElementById("UPDATE_USERPARAMS_HEIGHT").value;
        var userWeight = document.getElementById("UPDATE_USERPARAMS_WEIGHT").value;
        var paramsDate = document.getElementById("UPDATE_USERPARAMS_DATE").value;
        $(".alert .alertMessage").text("");

        if(Date.parse(paramsDate) > Date.now()) {alertAddMes("Неверная дата."); showAlert = true;}
        if(userHeight < 30  || userHeight>  300) {alertAddMes("Рост: 30-300 единиц."); showAlert = true;}
        if(userWeight < 10  || userWeight>  300) {alertAddMes("Вес: 10-300 единиц."); showAlert = true;}
        if(!Number.isInteger(Number.parseInt(id))) {alertAddMes("Перезагрузите страницу. Неверное значение идентификтора записи."); showAlert = true;}

        if(showAlert)
        {
            $(".alert").show('close');
            $(".alert").alert();
            return false;
        }
        closeAlert();
        body =
            {
                userHeight:userHeight,
                userWeight:userWeight,
                paramsDate:paramsDate,

            }
    }
    fetch(finalUrl,
        {
            method: 'PUT',
            headers: {'Content-Type': 'application/json','Authorization' : authHeader().Authorization},
            body : JSON.stringify(body)
        }
    ).then( response =>
    {
        if (response.status === 200)
        {
            messageShow("Запись успешно изменена.");
            if(table == "DietTable")
            {
                updateAllDiet();
            }
            else if(table == "ProductsUserCollection")
            {
                getAll('ProductsUserCollection',10,1,'null','null');
            }
            else if(table == "UsersParamsCollection")
            {
                getAll('UsersParamsCollection',10,1,'null','null');
            }
        }
        else
        {
            throw "Ошибка обновления. Измените значения полей."
        }

    }).catch(message => {messageShow(message)});

}

function edit(table,id)
{
    var finalUrl = url;
    if(table == "BDT" || table == "LDT" ||table == "DDDT" ||table == "ADT" ||table == "DNDT")
    {
        finalUrl += `/user/reports/${id}`;
    }
    else if(table == "ProductsUserCollection")
    {
        finalUrl += `/user/products/${id}`;
    }
    else if (table == "UsersParamsCollection")
    {
        finalUrl += `/user/usersparams/${id}`;
    }
    fetch(finalUrl,
        {
            method: 'GET',
            headers: {'Content-Type': 'application/json','Authorization' : authHeader().Authorization}
        }
    ).then( response =>
        {
            if (response.status === 200)
            {
                return response.json();
            }
            else
            {
                throw "Ошибка получения данных с сервера. Перезагрузите страницу и повторите попытку"
            }
        }
    )
        .then( pdata =>
        {
            if(table == "BDT" || table == "LDT" ||table == "DDDT" ||table == "ADT" ||table == "DNDT")
            {
                document.getElementById("REPORTID").value = pdata.id;
                document.getElementById("GRAMSUPDATE").value = pdata.dayGram;
                var date = new Date(pdata.reportDate);
                var curr_date = date.getDate();
                var curr_month = date.getMonth() + 1;
                var curr_year = date.getFullYear();
                var date_format = curr_year + "-" + (curr_month < 10? '0' + curr_month: curr_month) + "-" + (curr_date < 10? '0' + curr_date: curr_date);
                document.getElementById("UPDATE_DIET_DATE").value = date_format;
            }
            else if(table == "ProductsUserCollection")
            {
                document.getElementById("PRODUCTID").value = pdata.id;
                document.getElementById("UPDATE_USERPRODUCT_PRODUCTNAME").value = pdata.productName;
                document.getElementById("UPDATE_USERPRODUCT_CALORIESGRAM").value = pdata.caloriesGram;
                document.getElementById("UPDATE_USERPRODUCT_PROTEINSGRAM").value = pdata.proteinsGram;
                document.getElementById("UPDATE_USERPRODUCT_CARBSGRAM").value = pdata.carbohydratesGram;
                document.getElementById("UPDATE_USERPRODUCT_FATSGRAM").value = pdata.fatsGram;
                document.getElementById("UPDATE_USERPRODUCT_CATEGORY").value = pdata.foodCategory;

            }
            else if(table == "UsersParamsCollection")
            {
                document.getElementById("PARAMSID").value = pdata.id;
                document.getElementById("UPDATE_USERPARAMS_HEIGHT").value = pdata.userHeight;
                var date = new Date(pdata.paramsDate);
                var curr_date = date.getDate();
                var curr_month = date.getMonth() + 1;
                var curr_year = date.getFullYear();
                var date_format = curr_year + "-" + (curr_month < 10? '0' + curr_month: curr_month) + "-" + (curr_date < 10? '0' + curr_date: curr_date);
                document.getElementById("UPDATE_USERPARAMS_DATE").value = date_format;
                document.getElementById("UPDATE_USERPARAMS_WEIGHT").value = pdata.userWeight;
            }

        }).catch(message => {messageShow(message)});

}

function delete_cookie ( cookie_name )
{
    var cookie_date = new Date ( );  // Текущая дата и время
    cookie_date.setTime ( cookie_date.getTime() - 1 );
    document.cookie = cookie_name += "=; expires=" + cookie_date.toGMTString();
}
