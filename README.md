# Application
Airport App + Bank App<br><br>
<pre>
<h3>1. Определить тип приложения</h3>
    Тип: Веб-приложение с серверной частью.
    <br>    Функциональность:
        Поиск и бронирование билетов.
        Регистрация на рейс.
        Оплата билетов с помощью интегрированной банковской системы.
        Управление профилем пользователя.
        Просмотр истории заказов.
        Доступ к информации о рейсах.
<br><h3>2. Выбрать стратегию развёртывания</h3>
    Клиент-серверная архитектура:
        1. Банковская часть приложения будет развернута на одном из компьютеров.
        2. Клиентская часть приложения будет доступна через веб-браузер на другом компьютере.
        3. Связь между клиентской и серверной частями будет осуществляться через сокет.
<br><h3>3. Обосновать выбор технологии</h3>
    Клиент-серверная архитектура подходит для данного приложения, т.к.:
        1. Обеспечивает масштабируемость: серверную часть можно масштабировать для поддержки большего
        количества пользователей.
        2. Позволяет разделить функциональность приложения: серверная часть может обрабатывать сложные
        операции, а клиентская часть может быть простой и удобной для пользователя.
        3. Обеспечивает безопасность: серверная часть может быть защищена от несанкционированного доступа.
    <br>    Java:
        1. Подходит для разработки серверной части приложения благодаря надежности, безопасности
        и масштабируемости.
        2. Обеспечивает высокую производительность при обработке большого количества транзакций.
        3. Имеет широкое сообщество разработчиков и обширную библиотеку инструментов.
    <br>    React:
        1. Позволяет создавать современные, интерактивные пользовательские интерфейсы.
        2. Подходит для разработки веб-приложений, работающих в режиме реального времени.
        3. Облегчает разработку благодаря использованию компонентов и системы управления состоянием.
    <br>    C++:
        1. Подходит для разработки высокопроизводительных систем, где требуется низкоуровневый 
        контроль над аппаратным обеспечением.
        2. Обеспечивает высокую степень безопасности и надежности.
        3. Используется для разработки банковских систем, что обеспечивает совместимость с
        существующей инфраструктурой банка.
<br><h3>4. Указать показатели качества</h3>
    Производительность:
        1. Время отклика приложения.
        2. Пропускная способность системы.
        3. Масштабируемость при увеличении нагрузки.
    <br>    Безопасность:
        1. Защита данных пользователей.
        2. Предотвращение несанкционированного доступа.
        3. Соблюдение стандартов безопасности.
    <br>    Надежность:
        1. Доступность приложения 24/7.
        2. Устойчивость к ошибкам и сбоям.
        3. Минимизация времени простоя.
    <br>    Удобство использования:
        1. Простота навигации по приложению.
        2. Понятный и интуитивно понятный интерфейс.
        3. Доступность справочной информации и поддержки.
</pre>
