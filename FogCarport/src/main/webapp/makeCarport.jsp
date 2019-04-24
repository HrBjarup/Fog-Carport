<%-- 
    Document   : makeCarport
    Created on : 12-04-2019, 16:39:35
--%>

<jsp:include page='/header.jsp'></jsp:include>

    <div class="d-flex justify-content-center" >
        <!-- Form start -->
        <form action="FrontController" method="post" id="carport-form">
            
            <!-- Hidden input: &command=simpleorder -->
            <input type="hidden" name="command" value="simpleorder">
            
            <h1>Bestil Carport</h1>

            <!-- Height -->
            <div class="form-group">
                <label for="InputHeight">H�jde i cm</label>
                <input type="number"class="form-control" id="input-height" placeholder="Indtast h�jde" name="height">
                <p hidden class="text-error-color text-error-size">H�jde skal v�re mellem 200cm og 300cm</p>
            </div>

            <!-- Length -->
            <div class="form-group">
                <label for="InputLength">L�ngde i cm</label>
                <input type="number" class="form-control" id="input-length" placeholder="Indtast l�ngde" name="length">
                <p hidden class="text-error-color text-error-size">L�ngde skal v�re mellem 240cm og 720cm</p>
            </div>

            <!-- Width -->
            <div class="form-group">
                <label for="InputWidth">Bredde i cm</label>
                <input type="number" class="form-control" id="input-width" placeholder="Indtast bredde" name="width">
                <p hidden class="text-error-color text-error-size">Bredde skal v�re mellem 240cm og 720cm</p>
            </div>

            <!-- Checkbox -->
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="check-skur" name="shed" value="y">
                <label class="form-check-label" for="CheckSkur">V�lg Skur</label>
            </div>

            <!-- Button to submit -->
            <button type="submit" class="btn btn-primary" id="submit-btn" style="margin-top: 5px;">Bestil Carport</button>
            
        </form>
        <!-- Form end -->
    </div>

<jsp:include page='/footer.jsp'></jsp:include>
