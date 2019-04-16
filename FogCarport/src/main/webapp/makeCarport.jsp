<%-- 
    Document   : makeCarport
    Created on : 12-04-2019, 16:39:35
    Author     : Malte
--%>

<jsp:include page='/header.jsp'></jsp:include>

    <div class="d-flex justify-content-center" >
        <!-- Form start -->
        <form action="FrontController" method="post">
            
            <!-- Hidden input: &command=simpleorder -->
            <input type="hidden" name="command" value="simpleorder">
            
            <h1>Bestil Carport</h1>

            <!-- Height -->
            <div class="form-group">
                <label for="InputHeight">H�jde i cm</label>
                <input type="number" required class="form-control" id="InputHeight" placeholder="Indtast h�jde" name="height" min="1" max="400">
            </div>

            <!-- Length -->
            <div class="form-group">
                <label for="InputLength">L�ngde i cm</label>
                <input type="number" required class="form-control" id="InputLength" placeholder="Indtast l�ngde" name="length" min="1">
            </div>

            <!-- Width -->
            <div class="form-group">
                <label for="InputWidth">Bredde i cm</label>
                <input type="number" required class="form-control" id="InputWidth" placeholder="Indtast bredde" name="width" min="1">
            </div>

            <!-- Checkbox -->
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="CheckSkur" name="shed" value="y">
                <label class="form-check-label" for="CheckSkur">V�lg Skur</label>
            </div>

            <!-- Button to submit -->
            <button type="submit" class="btn btn-primary" style="margin-top: 5px;">Bestil Skur</button>
            
        </form>
        <!-- Form end -->
    </div>

<jsp:include page='/footer.jsp'></jsp:include>
