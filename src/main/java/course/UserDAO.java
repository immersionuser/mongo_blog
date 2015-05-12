    // validates that username is unique and insert into db
    public boolean addUser(String username, String password, String email) {

        String passwordHash = makePasswordHash(password, Integer.toString(random.nextInt()));

        Document user = new Document();
        user.put("_id", username);
        user.put("password", passwordHash);

        if (email != null && !email.equals("")) {
        	user.put("email", email);
        }

        try {
        	usersCollection.insertOne(user);
            return true;
        } catch (MongoWriteException e) {
            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
                System.out.println("Username already in use: " + username);
                return false;
            }
            throw e;
        }
    }
