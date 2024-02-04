package ru.javarush.katyshev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javarush.katyshev.model.Task;
import ru.javarush.katyshev.model.TaskDTO;
import ru.javarush.katyshev.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public String index(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
                        Model model) {

        List<Task> tasks = taskService.findAll((page - 1) * limit, limit);
        int count = taskService.getCount();
        int pagesCount = (int) Math.ceil(1.0 + count / limit);

        if (pagesCount > 1) {
            List<Integer> pages = IntStream.rangeClosed(1, pagesCount).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pages);
        }

        model.addAttribute("current_page", page);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("delete")
    public String delete(@RequestParam("id") int id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}")
    public String edit(Model model,
                       @PathVariable Integer id,
                       @RequestBody TaskDTO taskDTO) {

        if (isNull(id) || id <= 0) return "tasks";

        taskService.update(id, taskDTO);

        return index(1, 10, model);
    }

    @PostMapping("/")
    public String add(@RequestBody TaskDTO taskDTO, Model model) {
        taskService.save(taskDTO);
        return index(1, 10, model);
    }
}
